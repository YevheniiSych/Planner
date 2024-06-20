package com.planner.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planner.data.room.category.Category
import com.planner.data.use_case.category.CategoryUseCases
import com.planner.data.util.OrderType
import com.planner.data.util.category.CategoryOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private var getCategoriesJob: Job? = null

    init {
        getCategories(CategoryOrder.DateCreated(OrderType.DESC))
    }

    fun onEvent(event: HomeEvent) {
        when(event){
            is HomeEvent.CategoryEvent -> onCategoryEvent(event)
        }
    }

    private fun onCategoryEvent(event: HomeEvent.CategoryEvent) {
        when(event){
            is HomeEvent.CategoryEvent.AddNew -> {
                viewModelScope.launch {
                    val newCategory = Category(
                        title = event.categoryName
                    )
                    categoryUseCases.addCategory(newCategory)
                }
            }

            is HomeEvent.CategoryEvent.Delete -> {
                viewModelScope.launch {
                    categoryUseCases.deleteCategory(state.value.categoryToManage)
                }
            }

            is HomeEvent.CategoryEvent.Pin -> {
            }

            is HomeEvent.CategoryEvent.Selected -> {
                _state.value = state.value.copy(
                    selectedCategoryIndex = event.index,
                    selectedCategory = event.category
                )
            }

            is HomeEvent.CategoryEvent.ManageCategory -> {
                _state.value = state.value.copy(
                    categoryToManage = event.category
                )
            }
        }
    }

    private fun getCategories(categoryOrder: CategoryOrder) {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoryUseCases.getCategories(categoryOrder)
            .onEach {
                _state.value = state.value.copy(
                    _categories = it,
                    categoryOrder = categoryOrder
                )
            }
            .launchIn(viewModelScope)
    }

}