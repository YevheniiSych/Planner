package com.planner.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planner.data.room.category.Category
import com.planner.data.use_case.category.CategoryUseCases
import com.planner.data.use_case.task.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
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

            }
            is HomeEvent.TaskEvent.Complete -> {

            }
            is HomeEvent.TaskEvent.Delete -> {

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
            .onEach {
                _state.value = state.value.copy(
                    _categories = it
                )
            }
            .launchIn(viewModelScope)
    }

}