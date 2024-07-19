package com.planner.ui.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.planner.ui.task.components.SelectCategoryDropdownMenu
import com.planner.ui.task.components.TaskTitleInputField

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

    var taskText by rememberSaveable {
        mutableStateOf("")
    }

    val contentPadding = remember { 20.dp }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = contentPadding, end = contentPadding, top = contentPadding),
    ) {

        Icon(
            modifier = Modifier.clickable {
                onNavigateBack()
            },
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = "Back button"
        )


        SelectCategoryDropdownMenu(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(0.35f)
                .align(Alignment.End),
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

        TaskTitleInputField(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.3f),
            text = state.task.text,
            onValueChange = {
                onEvent(TaskDetailEvent.OnTitleInput(it))
            },
            onFocusChange = {}
        )
    }
}