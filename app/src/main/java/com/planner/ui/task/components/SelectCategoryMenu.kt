package com.planner.ui.task.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.planner.R
import com.planner.data.room.category.Category

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
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(0.5f),
                value = selectedCategory?.title ?: stringResource(R.string.no_category_title),
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    onExpandedChange(false)
                }
            ) {
                CategoryDropdownMenuItem(
                    title = "No Category",
                    isSelected = selectedCategory == null,
                    onClick = {
                        onNoCategoryClick()
                        onExpandedChange(false)
                    }
                )
                categories.forEach {
                    CategoryDropdownMenuItem(
                        title = it.title,
                        isSelected = selectedCategory?.id == it.id,
                        onClick = {
                            onItemClick(it)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryDropdownMenuItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = {
            Text(
                text = title,
                color = if (isSelected) {
                    Color.Blue
                } else {
                    Color.Black
                },
                fontSize = 16.sp,
            )
        },
        onClick = onClick
    )
}