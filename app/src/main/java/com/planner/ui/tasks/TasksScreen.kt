package com.planner.ui.tasks

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.planner.data.room.category.Category
import com.planner.ui.theme.SelectedCategoryItemColor
import com.planner.ui.theme.UnselectedCategoryItemColor

@Preview
@Composable
fun TasksScreen(
    viewModel: TasksViewModel = hiltViewModel()
) {

    CategoriesList(
        taskCategories = viewModel.state.value.categories,
        onItemClick = {

        },
        onEvent = {

        }
    )
}

@Composable
fun CategoriesList(
    taskCategories: List<Category>,
    onItemClick: (Category) -> Unit,
    onEvent: (TaskEvent) -> Unit
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
            itemsIndexed(taskCategories) { index, category ->

                val isSelected = selectedCategoryIndex == index

                CategoryItem(
                    category = category,
                    isSelected = isSelected,
                    onItemClick = {
                        selectedCategoryIndex = index
                        onItemClick(it)
                    },
                    onEvent = onEvent
                )

            }
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onItemClick: (Category) -> Unit,
    onEvent: (TaskEvent) -> Unit
) {

    var isCategoryMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .clip(shape = CircleShape)
            .background(
                color = if (isSelected) SelectedCategoryItemColor
                else UnselectedCategoryItemColor
            )
            .padding(horizontal = 15.dp, vertical = 2.dp)
            .pointerInput(true) {
                detectTapGestures(
                    onTap = { onItemClick(category) },
                    onLongPress = {
                        if (category.id != Category.CATEGORY_ALL_ID) {
                            isCategoryMenuVisible = true
                        }
                    }
                )
            }
    ) {
        Text(text = category.title)

        CategoryMenu(
            expanded = isCategoryMenuVisible,
            category = category,
            onDismissRequest = {
                isCategoryMenuVisible = false
            }
        ) {
            onEvent(it)
            isCategoryMenuVisible = false
        }
    }
}

@Composable
fun CategoryMenu(
    expanded: Boolean,
    category: Category,
    onDismissRequest: () -> Unit,
    onEvent: (TaskEvent) -> Unit
) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismissRequest) {
        DropdownMenuItem(
            text = { Text(text = "Pin") },
            onClick = { onEvent(TaskEvent.PinCategory(category)) }
        )
        DropdownMenuItem(
            text = { Text(text = "Add new") },
            onClick = { /* Handle add new */ }
        )
        DropdownMenuItem(
            text = { Text(text = "Delete") },
            onClick = { onEvent(TaskEvent.DeleteCategory(category)) }
        )
    }
}