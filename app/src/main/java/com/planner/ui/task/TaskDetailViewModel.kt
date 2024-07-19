@file:OptIn(FlowPreview::class)

package com.planner.ui.task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.planner.data.room.category.Category
import com.planner.data.room.task.Task
import com.planner.data.room.task.TaskId
import com.planner.data.use_case.category.CategoryUseCases
import com.planner.data.use_case.task.TaskUseCases
import com.planner.ui.nav.TaskDetailNav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val categoryUseCases: CategoryUseCases,
    private val taskUseCases: TaskUseCases,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(TaskDetailState())
    val stateFlow: StateFlow<TaskDetailState> = _stateFlow

    private var getCategoriesJob: Job? = null
    private var getTaskJob: Job? = null

    init {
        val taskId: TaskId = savedStateHandle.toRoute<TaskDetailNav>().taskId
        if (taskId != Task.INVALID_TASK_ID) {
            getTask(taskId)
        }
        getCategories()
        viewModelScope.launch {
            _stateFlow.debounce(1000)
                .collect { state ->
                    saveTaskTitle(state.task.text)
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            saveTaskTitle(stateFlow.value.task.text)
        }
    }

    fun onEvent(event: TaskDetailEvent) {
        when (event) {
            is TaskDetailEvent.OnTitleInput -> {
                _stateFlow.update {
                    it.copy(
                        task = it.task.copy(
                            text = event.text
                        ),
                    )
                }
            }

            is TaskDetailEvent.OnReminderSet -> {

            }

            is TaskDetailEvent.OnCategorySelected -> {
                updateTaskCategory(event.category)
            }

            TaskDetailEvent.OnNoCategorySelected -> {
                updateTaskCategory(null)
            }
        }
    }

    private fun updateTaskCategory(category: Category?) {
        viewModelScope.launch {
            taskUseCases.updateTaskUseCase(
                stateFlow.value.task.copy(
                    category = category
                )
            )
        }
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getCategories()
            .onEach { categories ->
                _stateFlow.update {
                    it.copy(
                        categoryMenuItems = categories
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getTask(taskId: TaskId) {
        getTaskJob?.cancel()
        getTaskJob = taskUseCases.getSingleTaskUseCase(taskId)
            .onEach { task ->
                _stateFlow.update {
                    it.copy(
                        task = task
                    )
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun saveTaskTitle(text: String) {
        taskUseCases.updateTaskUseCase(
            stateFlow.value.task.copy(
                text = text
            )
        )
    }
}