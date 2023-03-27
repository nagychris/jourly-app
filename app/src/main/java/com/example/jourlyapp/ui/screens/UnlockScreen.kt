package com.example.jourlyapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourlyapp.ui.components.shared.PageHeader
import com.example.jourlyapp.ui.components.shared.buttons.BaseButton
import com.example.jourlyapp.ui.components.shared.inputs.PasscodeTextField
import com.example.jourlyapp.viewmodel.auth.UnlockViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UnlockScreen(
    modifier: Modifier = Modifier,
    onUnlockClick: () -> Unit,
) {
    val viewModel: UnlockViewModel =
        viewModel(factory = UnlockViewModel.Factory)
    val scaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Scaffold(scaffoldState = scaffoldState) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .padding(bottom = paddingValues.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PageHeader(title = "Unlock Jourly")
            Text(
                "Please enter your passcode to unlock the app",
                modifier = modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.Start)
            )
            PasscodeTextField(
                value = viewModel.passcode.value,
                onPasscodeChange = {
                    viewModel.updatePasscode(it)
                },
                modifier = modifier.padding(bottom = 8.dp)
            )
            BaseButton(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp),
                enabled = viewModel.passcode.value.isNotEmpty(),
                onClick = {
                    if (viewModel.isPasscodeValid()) {
                        onUnlockClick()
                    } else {
                        coroutineScope.launch {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Sorry, wrong passcode!",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                },
            ) {
                Text(text = "Unlock", color = MaterialTheme.colors.onPrimary)
            }
            if (viewModel.errorMessage.value.isNotEmpty())
                Text(
                    viewModel.errorMessage.value,
                    color = MaterialTheme.colors.error
                )
        }

    }
}
