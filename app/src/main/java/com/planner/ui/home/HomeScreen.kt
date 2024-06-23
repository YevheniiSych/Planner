package com.planner.ui.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.planner.ui.components.ConfirmationDialog
import com.planner.ui.home.category.AddCategoryDialog
import com.planner.ui.home.category.CategoriesLayoutCallbacks
import com.planner.ui.home.category.CategoryItem
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

    var showTestingToast by remember {
        mutableStateOf(false)
    }

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

    if (showTestingToast) {
        Toast.makeText(LocalContext.current, state.selectedCategory.title, Toast.LENGTH_SHORT)
            .show()
        showTestingToast = false
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val addCategoryIconSize = 30.dp

            CategoryListLayout(
                modifier = Modifier
                    .weight(1f),
                categories = state.categories,
                selectedCategoryEntity = state.selectedCategory,
                callbacks = object : CategoriesLayoutCallbacks {
                    override fun onCategorySelected(index: Int, category: CategoryItem) {
                        onEvent(HomeEvent.CategoryEvent.Selected(index, category))
                        showTestingToast = true
                    }

                    override fun onMenuOpened(category: CategoryItem) {
                        onEvent(HomeEvent.CategoryEvent.ManageCategory(category))
                    }

                    override fun onPinItemClick(isPinned: Boolean) {
                        onEvent(HomeEvent.CategoryEvent.Pin(isPinned))
                    }

                    override fun onDeleteItemClick() {
                        isDeleteCategoryDialogVisible = true
                    }
                }
            )
            Icon(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(addCategoryIconSize)
                    .clickable { isAddNewCategoryDialogVisible = true },
                imageVector = Icons.Outlined.AddCircle,
                contentDescription = "Add category icon",
                tint = Color.Blue
            )
        }
    }
}