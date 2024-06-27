package com.planner.ui.home.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.planner.data.room.task.Task
import com.planner.ui.home.HomeEvent
import com.planner.ui.theme.Blue10
import com.planner.ui.theme.LightGray
import com.planner.ui.theme.LightYellow
import com.planner.ui.theme.SuperLightGray

@Composable
fun TaskListLayout(
    modifier: Modifier = Modifier,
    tasks: List<Task> = listOf(),
    onEvent: (HomeEvent.TaskEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.then(modifier),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(tasks) { task ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = if (task.isCompleted) LightYellow else LightGray,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = task.isCompleted, onCheckedChange = {
                        onEvent(HomeEvent.TaskEvent.Complete(task))
                    }
                )
                Text(
                    modifier = Modifier,
                    text = task.text,
                    fontSize = 16.sp,
                    style = TextStyle(
                        textDecoration = if (task.isCompleted) TextDecoration.LineThrough
                        else TextDecoration.None
                    )
                )
            }
        }
    }

}