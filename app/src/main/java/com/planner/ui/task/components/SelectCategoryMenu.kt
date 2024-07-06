package com.planner.ui.task.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.planner.data.room.category.Category
import com.planner.ui.theme.Blue10
import com.planner.ui.theme.BlueGray
import com.planner.ui.theme.MenuBg

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SelectCategoryDropdownMenu(
    expanded: Boolean = false,
    onExpandedChange: (expanded: Boolean) -> Unit = {},
    selectedCategory: Category? = null,
    categories: List<Category> = emptyList(),
    onItemClick: (Category) -> Unit = {},
    onNoCategoryClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier
                .wrapContentWidth(),
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(0.5f),
                value = selectedCategory?.title ?: "No Category No Category",
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MenuBg,
                    focusedContainerColor = MenuBg,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
                onExpandedChange(false)
            }) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "No category",
                            color = if (selectedCategory == null) {
                                Color.Blue
                            } else {
                                Color.Black
                            }
                        )
                    }, onClick = {
                        onNoCategoryClick()
                        onExpandedChange(false)
                    }
                )
                categories.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = it.title,
                                color = if (it.id == selectedCategory?.id) {
                                    Color.Blue
                                } else {
                                    Color.Black
                                }
                            )
                        }, onClick = {
                            onItemClick(it)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
    }
}