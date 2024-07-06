package com.planner.ui.task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.planner.ui.task.components.SelectCategoryDropdownMenu

@Preview
@Composable
fun TaskDetailScreen(
    onEvent: (TaskDetailEvent) -> Unit = {},
    state: TaskDetailState = TaskDetailState(),
    onNavigateBack: () -> Unit = {}
) {
    var isCategoryMenuExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    SelectCategoryDropdownMenu(
        expanded = isCategoryMenuExpanded,
        onExpandedChange = {
            isCategoryMenuExpanded = it
        },
        selectedCategory = state.selectedCategory,
        categories = state.categoryMenuItems,
        onItemClick = {

        },
        onNoCategoryClick = {

        }
    )
}