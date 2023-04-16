package com.example.jourlyapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourlyapp.R
import com.example.jourlyapp.ui.components.shared.PageHeader
import com.example.jourlyapp.ui.components.shared.buttons.BaseButton
import com.example.jourlyapp.ui.theme.Margins
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
            .padding(
                vertical = Margins.verticalLarge,
                horizontal = Margins.horizontal
            ),
    ) {
        PageHeader(
            title = stringResource(id = R.string.profile),
            modifier = modifier.padding(bottom = Margins.verticalLarge)
        )
        if (viewModel.userName.value.isNotEmpty()) {
            Card(
                modifier = modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(corner = CornerSize(16.dp))
            ) {
                Column(
                    modifier = modifier.padding(
                        vertical = Margins.vertical,
                        horizontal = Margins.horizontal
                    ),
                    verticalArrangement = Arrangement.spacedBy(Margins.vertical),
                    horizontalAlignment = Alignment.Start
                ) {
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

    }
}