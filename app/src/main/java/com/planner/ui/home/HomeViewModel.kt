package com.planner.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planner.data.room.category.CategoryEntity
import com.planner.data.use_case.category.CategoryUseCases
import com.planner.ui.home.category.CategoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private var categoryEntities: List<CategoryEntity> = emptyList()

    init {
        getCategories()
    }

    fun onEvent(event: HomeEvent) {
        when(event){
            is HomeEvent.CategoryEvent -> onCategoryEvent(event)
        }
    }

    private fun onCategoryEvent(event: HomeEvent.CategoryEvent) {
        when(event){
            is HomeEvent.CategoryEvent.AddNew -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val newCategoryEntity = CategoryEntity(
                        title = event.categoryName
                    )
                    categoryUseCases.addCategory(newCategoryEntity)
                }
            }

            is HomeEvent.CategoryEvent.Delete -> {
                viewModelScope.launch(Dispatchers.IO) {
                    state.value.categoryToManage.getAssociatedCategoryEntity()?.let {
                        categoryUseCases.deleteCategory(it)
                    }
                }
            }

            is HomeEvent.CategoryEvent.Pin -> {
                viewModelScope.launch(Dispatchers.IO) {
                    state.value.categoryToManage.getAssociatedCategoryEntity()?.copy(
                        isPinned = event.isPinned,
                        lastPinTime = System.currentTimeMillis()
                    )?.let {
                        categoryUseCases.updateCategoryUseCase(it)
                    }
                }
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

    private fun getCategories() {
        viewModelScope.launch {
            categoryUseCases.getCategories().collect {
                categoryEntities = it
                _state.value = state.value.copy(
                    _categories = it.toUIItems()
                )
            }
        }
    }

    private fun CategoryItem.getAssociatedCategoryEntity() =
        categoryEntities.firstOrNull { this.id == it.id }

    private fun List<CategoryEntity>.toUIItems() = this.map {
        CategoryItem(
            id = it.id,
            title = it.title,
            isPinned = it.isPinned
        )
    }

}