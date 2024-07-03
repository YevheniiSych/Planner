package com.planner.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planner.data.room.category.Category
import com.planner.data.room.task.Task
import com.planner.data.use_case.category.CategoryUseCases
import com.planner.data.use_case.task.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    private val taskUseCases: TaskUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<HomeUIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getCategoriesJob: Job? = null

    private var recentlyDeletedTask: Task? = null

    init {
        getCategories()
        getTasks()
    }

    fun onEvent(event: HomeEvent) {
        when(event){
            is HomeEvent.CategoryEvent -> onCategoryEvent(event)
            is HomeEvent.TaskEvent -> onTaskEvent(event)
        }
    }

    private fun onTaskEvent(event: HomeEvent.TaskEvent) {
        when (event) {
            is HomeEvent.TaskEvent.AddNew -> {
                viewModelScope.launch {
                    taskUseCases.addTaskUseCase(
                        Task(
                            text = event.text,
                            createdAt = System.currentTimeMillis(),
                            categoryId = event.categoryId,
                            reminderTime = event.reminderTime,
                            isReminderEnabled = event.reminderTime != null
                        )
                    )
                }
            }
            is HomeEvent.TaskEvent.Complete -> {
                viewModelScope.launch {
                    taskUseCases.updateTaskUseCase(
                        event.task.copy(
                            isCompleted = !event.task.isCompleted
                        )
                    )
                }
            }
            is HomeEvent.TaskEvent.Delete -> {
                viewModelScope.launch {
                    taskUseCases.deleteTaskUseCase(event.task)
                    recentlyDeletedTask = event.task
                    _eventFlow.emit(HomeUIEvent.OnTaskDeleted)
                }
            }

            HomeEvent.TaskEvent.Restore -> {
                viewModelScope.launch {
                    taskUseCases.addTaskUseCase(recentlyDeletedTask ?: return@launch)
                    recentlyDeletedTask = null
                }
            }
        }
    }

    private fun onCategoryEvent(event: HomeEvent.CategoryEvent) {
        when(event){
            is HomeEvent.CategoryEvent.AddNew -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val newCategory = Category(
                        title = event.categoryName
                    )
                    categoryUseCases.addCategory(newCategory)
                }
            }

            is HomeEvent.CategoryEvent.Delete -> {
                viewModelScope.launch(Dispatchers.IO) {
                    categoryUseCases.deleteCategory(event.category)
                    if (event.category.id == state.value.selectedCategory.id) {
                        _state.update {
                            it.copy(selectedCategory = Category.CATEGORY_ALL)
                        }
                    }
                }
            }

            is HomeEvent.CategoryEvent.Pin -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val isPinned = !event.category.isPinned
                    categoryUseCases.updateCategoryUseCase(
                        event.category.copy(
                            isPinned = isPinned,
                            lastPinTime = if (isPinned) System.currentTimeMillis()
                            else event.category.lastPinTime
                        )
                    )
                }
            }

            is HomeEvent.CategoryEvent.Selected -> {
                _state.update {
                    it.copy(selectedCategory = event.category)
                }
            }

            is HomeEvent.CategoryEvent.EditTitle -> {
                viewModelScope.launch {
                    categoryUseCases.updateCategoryUseCase(
                        event.category.copy(
                            title = event.newTitle
                        )
                    )
                }
            }
        }
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getCategories()
            .map {
                listOf(Category.CATEGORY_ALL) + it
            }
            .onEach { categories ->
                _state.update {
                    it.copy(categories = categories)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getTasks() {
        taskUseCases.getTasksUseCase.getAllTasks().onEach { tasks ->
            _state.update {
                it.copy(tasks = tasks)
            }
        }.launchIn(viewModelScope)
    }

}