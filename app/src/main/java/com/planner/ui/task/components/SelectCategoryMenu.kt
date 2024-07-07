package com.planner.ui.task.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.planner.R
import com.planner.data.room.category.Category

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SelectCategoryDropdownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onExpandedChange: (expanded: Boolean) -> Unit = {},
    selectedCategory: Category? = null,
    categories: List<Category> = emptyList(),
    onItemClick: (Category) -> Unit = {},
    onNoCategoryClick: () -> Unit = {}
) {
    ExposedDropdownMenuBox(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(30.dp),
                color = Color.Blue
            )
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .then(modifier),
        expanded = expanded,
        onExpandedChange = onExpandedChange
    ) {

        CategoryMenuSpinner(
            text = selectedCategory?.title ?: stringResource(R.string.no_category_title),
            isMenuExpanded = expanded,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryMenuSpinner(
    text: String,
    isMenuExpanded: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isMenuExpanded)
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
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        onClick = onClick
    )
}