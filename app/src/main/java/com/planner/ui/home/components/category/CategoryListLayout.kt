package com.planner.ui.home.components.category

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.planner.data.room.category.Category
import com.planner.ui.components.ConfirmationDialog
import com.planner.ui.theme.Blue10
import com.planner.ui.theme.LightBlue
import com.planner.ui.theme.LightYellow
import com.planner.ui.theme.Yellow

@Composable
fun CategoryListLayout(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    selectedCategory: Category,
    callbacks: CategoryLayoutCallbacks
) {

    Column(
        modifier = modifier
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(
                items = categories,
                key = { it.id }
            ) { category ->

                var isDeleteCategoryDialogVisible by rememberSaveable {
                    mutableStateOf(false)
                }

                val isSelected = selectedCategory.id == category.id

                if (isDeleteCategoryDialogVisible) {
                    ConfirmationDialog(
                        title = "Delete the category?",
                        text = "All tasks in this category will be deleted.",
                        onConfirm = {
                            callbacks.onDeleteCategory(category)
                            isDeleteCategoryDialogVisible = false
                        },
                        onDismiss = {
                            isDeleteCategoryDialogVisible = false
                        }
                    )
                }

                CategoryItem(
                    category = category,
                    isSelected = isSelected,
                    onDelete = {
                        isDeleteCategoryDialogVisible = true
                    },
                    onPin = {
                        callbacks.onPinCategory(category)
                    },
                    onEditTitle = {
                        callbacks.onEditTitle(
                            newTitle = it,
                            category = category
                        )
                    },
                    onClick = {
                        callbacks.onCategorySelected(category)
                    }
                )

            }
        }
    }
}

@Composable
private fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onDelete: () -> Unit,
    onPin: () -> Unit,
    onEditTitle: (title: String) -> Unit,
    onClick: () -> Unit
) {
    var isCategoryMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var isAddEditCategoryDialogVisible by rememberSaveable {
        mutableStateOf(false)
    }

    if (isAddEditCategoryDialogVisible) {
        AddEditCategoryDialog(
            text = category.title,
            onDismiss = { isAddEditCategoryDialogVisible = false },
            onSaveButtonClick = {
                onEditTitle(it)
                isAddEditCategoryDialogVisible = false
            }
        )
    }

    Box(
        modifier = Modifier
            .clip(shape = CircleShape)
            .background(
                color = when {
                    isSelected && category.isPinned -> Yellow
                    isSelected -> LightBlue
                    category.isPinned -> LightYellow
                    else -> Blue10
                }
            )
            .padding(horizontal = 15.dp, vertical = 2.dp)
            .pointerInput(category) {
                detectTapGestures(
                    onTap = {
                        onClick()
                    },
                    onLongPress = {
                        if (category.id != Category.CATEGORY_ALL_ID) {
                            isCategoryMenuVisible = true
                        }
                    }
                )
            }
    ) {
        Text(text = category.title)

        DropdownMenu(
            expanded = isCategoryMenuVisible,
            onDismissRequest = {
                isCategoryMenuVisible = false
            },
        ) {
            DropdownMenuItem(
                text = { Text(text = if (category.isPinned) "Unpin" else "Pin") },
                onClick = {
                    isCategoryMenuVisible = false
                    onPin()
                }
            )
            DropdownMenuItem(
                text = { Text(text = "Edit") },
                onClick = {
                    isCategoryMenuVisible = false
                    isAddEditCategoryDialogVisible = true
                }
            )
            DropdownMenuItem(
                text = { Text(text = "Delete") },
                onClick = {
                    isCategoryMenuVisible = false
                    onDelete()
                }
            )
        }
    }
}