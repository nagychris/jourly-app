package com.example.jourlyapp.ui.components.journal

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.SnackbarResult
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourlyapp.R
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.ui.theme.Margins
import com.example.jourlyapp.viewmodel.EntryModalViewModel
import kotlinx.coroutines.CoroutineScope
import java.time.LocalDateTime

@Composable
@ExperimentalMaterial3Api
fun AddEntryModalContent(
    modifier: Modifier,
    coroutineScope: CoroutineScope,
    onMoodIconClick: suspend () -> Unit,
    onExpandClick: () -> Unit,
    onCollapseClick: () -> Unit,
    isFullScreen: Boolean,
    onDetailedClose: () -> Unit
) {
    val viewModel: EntryModalViewModel =
        viewModel(factory = EntryModalViewModel.Factory)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(
                horizontal = Margins.horizontalLarge,
                vertical = Margins.verticalLarge
            ),
        verticalArrangement = Arrangement.spacedBy(Margins.verticalLarge)
    ) {
        // This IconButton could be removed to use only the ModalBottomSheet already present draggable functionality
        if (!isFullScreen) {
            IconButton(
                onClick = onExpandClick
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_baseline_keyboard_arrow_up_24),
                    contentDescription = "Expand for more detailed entry",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        } else {
            IconButton(
                onClick = {
                    viewModel.updateMood(Mood.None)
                    onCollapseClick()
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24),
                    contentDescription = "Collapse for quick entry",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }

        Text(
            text = stringResource(R.string.quickEntryQuestion),
            style = MaterialTheme.typography.bodyLarge
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Margins.vertical),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MoodIconsRow(
                coroutineScope = coroutineScope,
                onMoodIconClick = onMoodIconClick,
                onExpandClick = onExpandClick,
                viewModel = viewModel
            )
        }

        if (isFullScreen) {
            AddDetailedEntryModalContent(
                onClose = onDetailedClose,
                viewModel = viewModel
            )
        }
    }
}

fun addQuickEntry(
    viewModel: EntryModalViewModel,
    mood: Mood
) {
    viewModel.createNewQuickEntry(
        date = LocalDateTime.now(),
        mood = mood
    )
}