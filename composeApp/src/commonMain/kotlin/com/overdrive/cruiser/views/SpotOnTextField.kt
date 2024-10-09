package com.overdrive.cruiser.views

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun SpotOnTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        backgroundColor = Color.Transparent,
        cursorColor = Color.Gray,
        focusedLabelColor = Color.DarkGray,
        unfocusedLabelColor = Color.Gray,
        textColor = Color.DarkGray
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Text
    ),
    textStyle: TextStyle = LocalTextStyle.current,
    singleLine: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    SpotOnField(modifier = modifier) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            keyboardOptions = keyboardOptions,
            modifier = modifier,
            colors = colors,
            textStyle = textStyle,
            singleLine = singleLine,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon
        )
    }
}
