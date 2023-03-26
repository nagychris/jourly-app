package com.example.jourlyapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourlyapp.ui.components.shared.buttons.BaseButton
import com.example.jourlyapp.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onChangeUserClick: () -> Unit
) {
    val viewModel: ProfileViewModel =
        viewModel(factory = ProfileViewModel.Factory)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (viewModel.userName.value.isNotEmpty()) {
            Text(text = "This is ${viewModel.userName.value}'s Journal.")
            BaseButton(
                onClick = onChangeUserClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                )
            ) {
                Text("Delete User Details")
            }
        }

    }
}