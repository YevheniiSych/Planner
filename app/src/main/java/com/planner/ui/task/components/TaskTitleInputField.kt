package com.planner.ui.task.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTitleInputField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onFocusChange: (FocusState) -> Unit = {},
) {
    val fontSize = remember {
        18.sp
    }
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier
            .then(modifier)
            .onFocusChanged {
                onFocusChange(it)
            },
        textStyle = TextStyle(
            fontSize = fontSize,
        ),
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = text,
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
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = false,
                enabled = true,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(0.dp),
            )
        }
    )
}