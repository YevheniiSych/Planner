package com.planner.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val categoryUseCases: CategoryUseCases,
    private val taskUseCases: TaskUseCases,
) : ViewModel() {

    private val _stateLiveData = MutableLiveData<TaskDetailState>()
    val stateLiveData: LiveData<TaskDetailState>
        get() = _stateLiveData

    private var getCategoriesJob: Job? = null
    private var getTaskJob: Job? = null

    init {
        val taskId: TaskId = savedStateHandle.toRoute<TaskDetailNav>().taskId
        if (taskId != Task.INVALID_TASK_ID) {
            getTask(taskId)
        }
        getCategories()
    }

    fun onEvent(event: TaskDetailEvent) {
        when (event) {
            is TaskDetailEvent.OnTitleInput -> {
                viewModelScope.launch(Dispatchers.IO) {
                    taskUseCases.updateTaskUseCase(
                        stateLiveData.value?.task?.copy(
                            text = event.text
                        ) ?: return@launch
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
                stateLiveData.value?.task?.copy(
                    category = category
                ) ?: return@launch
            )
        }
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getCategories()
            .onEach { categories ->
                _stateLiveData.value = stateLiveData.value?.copy(
                    categoryMenuItems = categories
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getTask(taskId: TaskId) {
        getTaskJob?.cancel()
        getTaskJob = taskUseCases.getSingleTaskUseCase(taskId)
            .onEach { task ->
                _stateLiveData.value = stateLiveData.value?.copy(
                    task = task
                )
            }.launchIn(viewModelScope)
    }
}