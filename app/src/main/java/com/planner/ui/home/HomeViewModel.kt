package com.planner.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planner.data.use_case.category.CategoryUseCases
import com.planner.data.util.OrderType
import com.planner.data.util.category.CategoryOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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