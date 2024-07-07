package com.planner.ui.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.planner.ui.task.components.SelectCategoryDropdownMenu
import com.planner.ui.task.components.TaskInputField

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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        SelectCategoryDropdownMenu(
            modifier = Modifier
                .fillMaxWidth(0.35f),
            expanded = isCategoryMenuExpanded,
            onExpandedChange = {
                isCategoryMenuExpanded = it
            },
            selectedCategory = state.task.category,
            categories = state.categoryMenuItems,
            onItemClick = {
                onEvent(TaskDetailEvent.OnCategorySelected(it))
            },
            onNoCategoryClick = {
                onEvent(TaskDetailEvent.OnNoCategorySelected)
            }
        )

        TaskInputField(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f),
            text = "Hello world",
            onValueChange = {},
            onFocusChange = {}
        )
    }
}