package com.planner.ui.task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.planner.data.room.category.Category
import com.planner.data.room.task.TaskId
import com.planner.data.use_case.category.CategoryUseCases
import com.planner.data.use_case.task.TaskUseCases
import com.planner.ui.nav.TaskDetailNav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    private val taskUseCases: TaskUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(TaskDetailState())
    val stateFlow: StateFlow<TaskDetailState> = _stateFlow

    private var getCategoriesJob: Job? = null

    private val taskId: TaskId = savedStateHandle.toRoute<TaskDetailNav>().taskId

    init {
        getCategories()
    }

    fun onEvent(event: TaskDetailEvent) {
        when (event) {
            is TaskDetailEvent.OnTitleInput -> {

            }

            is TaskDetailEvent.OnReminderSet -> {

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
                _stateFlow.update {
                    it.copy(categories = categories)
                }
            }
            .launchIn(viewModelScope)
    }
}