package com.planner.ui.home.category

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
    selectedCategoryIndex: Int,
    callbacks: CategoriesLayoutCallbacks
) {

    Column(
        modifier = modifier
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            itemsIndexed(
                items = categories,
                key = { _, category -> category.id }) { index, category ->

                var isCategoryMenuVisible by rememberSaveable {
                    mutableStateOf(false)
                }

                var isDeleteCategoryDialogVisible by rememberSaveable {
                    mutableStateOf(false)
                }

                val isSelected = selectedCategoryIndex == index

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
                                    callbacks.onCategorySelected(index)
                                },
                                onLongPress = {
                                    if (category.id != Category.CATEGORY_ALL_ID) {
                                        isCategoryMenuVisible = true
//                                        callbacks.onMenuOpened(category)
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
                                callbacks.onPinItemClick(category)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Delete") },
                            onClick = {
                                isCategoryMenuVisible = false
                                callbacks.onDeleteCategory(category)
                            }
                        )
                    }
                }

            }
        }
    }
}