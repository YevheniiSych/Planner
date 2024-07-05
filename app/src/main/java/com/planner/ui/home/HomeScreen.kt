package com.planner.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.planner.data.room.category.Category
import com.planner.data.room.task.Task
import com.planner.data.room.task.TaskId
import com.planner.ui.home.components.category.AddEditCategoryDialog
import com.planner.ui.home.components.category.CategoryLayoutCallbacks
import com.planner.ui.home.components.category.CategoryListLayout
import com.planner.ui.home.components.task.TaskLayoutCallbacks
import com.planner.ui.home.components.task.TaskListLayout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    eventFlow: Flow<HomeUIEvent>,
    onEditTask: (TaskId) -> Unit,
    onCreateNewTask: () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }


    var isAddNewCategoryDialogVisible by rememberSaveable {
        mutableStateOf(false)
    }

    if (isAddNewCategoryDialogVisible) {
        AddEditCategoryDialog(
            onDismiss = {
                isAddNewCategoryDialogVisible = false
            },
            onSaveButtonClick = {
                isAddNewCategoryDialogVisible = false
                onEvent(HomeEvent.CategoryEvent.AddNew(it))
            }
        )
    }

    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                HomeUIEvent.OnTaskDeleted -> {
                    snackbarHostState.showDeleteTaskSnackbar {
                        onEvent(HomeEvent.TaskEvent.Restore)
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val addCategoryIconSize = 30.dp

                CategoryListLayout(
                    modifier = Modifier
                        .weight(1f),
                    categories = state.categories,
                    selectedCategory = state.selectedCategory,
                    callbacks = object : CategoryLayoutCallbacks {
                        override fun onCategorySelected(category: Category) {
                            onEvent(HomeEvent.CategoryEvent.Selected(category))
                        }

                        override fun onPinCategory(category: Category) {
                            onEvent(HomeEvent.CategoryEvent.Pin(category))
                        }

                        override fun onDeleteCategory(category: Category) {
                            onEvent(HomeEvent.CategoryEvent.Delete(category))
                        }

                        override fun onEditTitle(newTitle: String, category: Category) {
                            onEvent(HomeEvent.CategoryEvent.EditTitle(newTitle, category))
                        }
                    }
                )
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(addCategoryIconSize)
                        .clickable { isAddNewCategoryDialogVisible = true },
                    imageVector = Icons.Outlined.AddCircle,
                    contentDescription = "Add category icon",
                    tint = Color.Blue
                )
            }
            Box(modifier = Modifier.fillMaxSize()) {
                TaskListLayout(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
                    tasks = state.tasks,
                    callbacks = object : TaskLayoutCallbacks {
                        override fun onDelete(task: Task) {
                            onEvent(HomeEvent.TaskEvent.Delete(task))
                        }

                        override fun onComplete(task: Task) {
                            onEvent(HomeEvent.TaskEvent.Complete(task))
                        }

                        override fun onTaskClick(taskId: TaskId) {
                            onEditTask(taskId)
                        }

                    }
                )
                LargeFloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 30.dp, end = 30.dp),
                    onClick = {
                        onCreateNewTask()
//                        onEvent(
//                            HomeEvent.TaskEvent.AddNew("New task ${state.tasks.size + 1}")
//                        )
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Add, "Add task button")
                }
            }

        }

    }
}

private suspend fun SnackbarHostState.showDeleteTaskSnackbar(onUndoClick: () -> Unit) {
    val result = this.showSnackbar(
        message = "Task has been deleted.",
        actionLabel = "Undo",
        duration = SnackbarDuration.Long
    )
    when (result) {
        SnackbarResult.Dismissed -> {}
        SnackbarResult.ActionPerformed -> onUndoClick()
    }
}