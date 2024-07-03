package com.planner.ui.home.components.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.planner.data.room.task.Task
import com.planner.ui.theme.LightGray
import com.planner.ui.theme.LightYellow

@Composable
fun TaskListLayout(
    modifier: Modifier = Modifier,
    tasks: List<Task> = listOf(),
    callbacks: TaskLayoutCallbacks
) {
    LazyColumn(
        modifier = Modifier.then(modifier),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = tasks, key = { it.id }) { task ->
            val updatedTask by rememberUpdatedState(newValue = task)
            TaskItem(
                task = task,
                onDelete = { callbacks.onDelete(updatedTask) },
                onComplete = { callbacks.onComplete(task) }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onComplete: () -> Unit,
    onDelete: () -> Unit
) {
    val cornerRadius = 10.dp

    val dismissState = rememberSwipeToDismissBoxState(confirmValueChange = {
        when (it) {
            SwipeToDismissBoxValue.StartToEnd -> {
            }

            SwipeToDismissBoxValue.EndToStart -> {
                onDelete()
            }

            SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
        }
        return@rememberSwipeToDismissBoxState false
    },
        // positional threshold of 25%
        positionalThreshold = { it * .75f }
    )
    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        backgroundContent = {
            DismissTaskBackground(
                dismissState = dismissState, cornerRadius = cornerRadius
            )
        },
        content = {
            TaskItemContent(
                task = task,
                cornerRadius = cornerRadius,
                onComplete = onComplete
            )
        }
    )

}

@Composable
private fun TaskItemContent(
    task: Task, cornerRadius: Dp, onComplete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (task.isCompleted) LightYellow else LightGray,
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(checked = task.isCompleted, onCheckedChange = {
            onComplete()
        })
        Text(
            modifier = Modifier, text = task.text, fontSize = 16.sp, style = TextStyle(
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough
                else TextDecoration.None
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DismissTaskBackground(
    dismissState: SwipeToDismissBoxState, cornerRadius: Dp
) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Color(0xFF1DE9B6)
        SwipeToDismissBoxValue.EndToStart -> Color(0xFFFF1744)
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = color, shape = RoundedCornerShape(cornerRadius)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite, contentDescription = "Archive"
        )
        Spacer(modifier = Modifier)
        Icon(
            imageVector = Icons.Default.Delete, contentDescription = "delete"
        )
    }
}