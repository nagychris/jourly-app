package com.example.jourlyapp.ui.components.shared.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jourlyapp.R

@Composable
fun PasscodeTextField(
    value: String,
    onPasscodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done
) {
    val label = "Passcode"

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var isPasscodeVisible by remember { mutableStateOf(false) }

        val icon = if (isPasscodeVisible)
            painterResource(id = R.drawable.baseline_visibility_24)
        else
            painterResource(id = R.drawable.baseline_visibility_off_24)

        BaseTextField(
            value = value,
            singleLine = true,
            modifier = modifier.fillMaxWidth(),
            onValueChange = onPasscodeChange,
            placeholder = { Text(text = label) },
            label = { Text(text = label) },
            trailingIcon = {
                IconButton(onClick = {
                    isPasscodeVisible = !isPasscodeVisible
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Visibility Icon",
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction,
            ),
            visualTransformation = if (isPasscodeVisible)
                VisualTransformation.None
            else PasswordVisualTransformation(),
        )
    }
}

@Composable
@Preview
fun PasscodeTextFieldPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PasscodeTextField("", {}, Modifier.fillMaxSize())
    }
}