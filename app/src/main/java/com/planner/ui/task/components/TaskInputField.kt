package com.planner.ui.task.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun TaskInputField(
    text: String = "",
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    onFocusChange: (FocusState) -> Unit = {},
) {
    val fontSize = remember {
        16.sp
    }
    TextField(
        value = text,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            fontSize = fontSize
        ),
        placeholder = {
            Text(text = "Input here...", fontSize = fontSize)
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        ),
        modifier = Modifier
            .onFocusChanged {
                onFocusChange(it)
            }
            .then(modifier)
    )
}