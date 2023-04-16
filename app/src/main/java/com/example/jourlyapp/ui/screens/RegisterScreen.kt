package com.example.jourlyapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourlyapp.R
import com.example.jourlyapp.ui.components.shared.PageHeader
import com.example.jourlyapp.ui.components.shared.buttons.BaseButton
import com.example.jourlyapp.ui.components.shared.inputs.BaseTextField
import com.example.jourlyapp.ui.components.shared.inputs.PasscodeTextField
import com.example.jourlyapp.ui.theme.Typography
import com.example.jourlyapp.viewmodel.auth.RegisterUserViewModel


@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onRegisterClick: () -> Unit,
) {
    val viewModel: RegisterUserViewModel =
        viewModel(factory = RegisterUserViewModel.Factory)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        PageHeader(
            title = stringResource(R.string.welcomeText),
        )
        Text(
            text = stringResource(R.string.userNameLabel),
            fontSize = Typography.bodyMedium.fontSize
        )
        BaseTextField(
            value = viewModel.userName.value,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .testTag("userNameField"),
            onValueChange = { viewModel.updateUserName(it) },
            label = { Text(stringResource(R.string.userName)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        Text(
            text = stringResource(R.string.passcodeLabel),
            fontSize = Typography.bodyMedium.fontSize
        )
        PasscodeTextField(
            value = viewModel.userPasscode.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .testTag("passcodeField"),
            onPasscodeChange = { viewModel.updateUserPasscode(it) }
        )
        BaseButton(
            onClick = {
                if (viewModel.isUserValid()) {
                    viewModel.createUser()
                    onRegisterClick()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.isUserValid(),
        ) {
            Text(stringResource(id = R.string.save))
        }
    }
}