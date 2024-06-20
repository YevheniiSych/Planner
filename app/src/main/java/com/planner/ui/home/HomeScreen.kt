package com.planner.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.planner.data.room.category.Category
import com.planner.ui.components.ConfirmationDialog
import com.planner.ui.home.category.AddCategoryDialog
import com.planner.ui.home.category.CategoriesLayoutCallbacks
import com.planner.ui.home.category.CategoryListLayout

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {

    var isAddNewCategoryDialogVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var isDeleteCategoryDialogVisible by rememberSaveable {
        mutableStateOf(false)
    }

    CategoryListLayout(
        categories = state.categories,
        callbacks = object : CategoriesLayoutCallbacks {
            override fun onCategorySelected(index: Int, category: Category) {
                onEvent(HomeEvent.CategoryEvent.Selected(index, category))
            }

            override fun onMenuOpened(category: Category) {
                onEvent(HomeEvent.CategoryEvent.ManageCategory(category))
            }

            override fun onPinItemClick(category: Category) {
//                onEvent(HomeEvent.CategoryEvent.Pin)
            }

            override fun onDeleteItemClick(category: Category) {
                isDeleteCategoryDialogVisible = true
            }

            override fun onAddNewItemClick() {
                isAddNewCategoryDialogVisible = true
            }

        }
    )

    if (isAddNewCategoryDialogVisible) {
        AddCategoryDialog(
            onDismiss = {
                isAddNewCategoryDialogVisible = false
            },
            onSaveButtonClick = {
                isAddNewCategoryDialogVisible = false
                onEvent(HomeEvent.CategoryEvent.AddNew(it))
            }
        )
    }

    if (isDeleteCategoryDialogVisible) {
        ConfirmationDialog(
            title = "Delete the category?",
            text = "All tasks in this category will be deleted.",
            onConfirm = {
                isDeleteCategoryDialogVisible = false
                onEvent(HomeEvent.CategoryEvent.Delete)
            },
            onDismiss = {
                isDeleteCategoryDialogVisible = false
            }
        )
    }
}