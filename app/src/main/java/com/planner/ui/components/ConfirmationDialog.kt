package com.planner.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ConfirmationDialog(
    title: String = "",
    text: String = "",
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = text) },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "No")
            }
        }
    )
}