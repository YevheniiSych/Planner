package com.planner.ui.home.category

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.planner.data.room.category.Category
import com.planner.ui.theme.Blue10
import com.planner.ui.theme.LightBlue

@Composable
fun CategoryListLayout(
    categories: List<Category>,
    callbacks: CategoriesLayoutCallbacks
) {
    var selectedCategoryIndex by remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            itemsIndexed(categories) { index, category ->

                var isCategoryMenuVisible by rememberSaveable {
                    mutableStateOf(false)
                }

                val isSelected = selectedCategoryIndex == index

                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(
                            color = if (isSelected) LightBlue
                            else Blue10
                        )
                        .padding(horizontal = 15.dp, vertical = 2.dp)
                        .pointerInput(true) {
                            detectTapGestures(
                                onTap = {
                                    selectedCategoryIndex = index
                                    callbacks.onCategorySelected(index, category)
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
                            text = { Text(text = "Pin") },
                            onClick = {
                                isCategoryMenuVisible = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Add new") },
                            onClick = {
                                isCategoryMenuVisible = false
                                callbacks.onAddNewItemClick()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Delete") },
                            onClick = {
                                isCategoryMenuVisible = false
                            }
                        )
                    }
                }

            }
        }
    }
}