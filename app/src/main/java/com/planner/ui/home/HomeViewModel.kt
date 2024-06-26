package com.planner.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planner.data.room.category.Category
import com.planner.data.room.task.Task
import com.planner.data.use_case.category.CategoryUseCases
import com.planner.data.use_case.task.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    private val taskUseCases: TaskUseCases
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private var getCategoriesJob: Job? = null

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
                    categoryUseCases.deleteCategory(state.value.categoryToManage)
                    _state.value = state.value.copy(
                        selectedCategoryIndex = 0
                    )
                }
            }

            is HomeEvent.CategoryEvent.Pin -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val pinnedCategory = state.value.categoryToManage.copy(
                        isPinned = event.isPinned,
                        lastPinTime = System.currentTimeMillis()
                    )
                    categoryUseCases.updateCategoryUseCase(pinnedCategory)
                }
            }

            is HomeEvent.CategoryEvent.Selected -> {
                _state.value = state.value.copy(
                    selectedCategoryIndex = event.index
                )
            }

            is HomeEvent.CategoryEvent.ManageCategory -> {
                _state.value = state.value.copy(
                    categoryToManage = event.category
                )
            }
        }
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getCategories()
            .map {
                listOf(Category.CATEGORY_ALL) + it
            }
            .onEach {
                _state.value = state.value.copy(
                    categories = it
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getTasks() {
        taskUseCases.getTasksUseCase.getAllTasks().onEach { tasks ->
            _state.value = state.value.copy(
                tasks = tasks
            )
        }.launchIn(viewModelScope)
    }

}