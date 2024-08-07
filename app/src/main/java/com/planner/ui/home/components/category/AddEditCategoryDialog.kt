package com.planner.ui.home.components.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.planner.ui.theme.Blue10

@Preview
@Composable
fun AddEditCategoryDialog(
    onDismiss: () -> Unit = {},
    onSaveButtonClick: (categoryName: String) -> Unit = {},
    text: String = ""
) {

    val focusRequester = remember { FocusRequester() }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false
        )
    ) {
        var categoryName by remember {
            mutableStateOf(text)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .background(color = Blue10, shape = RoundedCornerShape(15.dp))
                .padding(15.dp),
        ) {

            Text(
                text = "Add new category",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 16.sp
            )

            val maxChar = 15

            TextField(
                value = categoryName,
                onValueChange = {
                    if (maxChar >= it.length) {
                        categoryName = it
                    }
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black
                ),
                placeholder = { Text("Input here...", fontSize = 14.sp) },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = Color.LightGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.isFocused) {

                        }
                    },
                shape = RoundedCornerShape(10.dp)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 10.dp)
            ) {
                TextButton(onClick = {
                    onDismiss()
                }){
                    Text(text = "Cancel".uppercase(), color = Color.Blue)
                }

                TextButton(onClick = {
                    onSaveButtonClick(categoryName)
                }){
                    Text(text = "Save".uppercase(), color = Color.Blue)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}