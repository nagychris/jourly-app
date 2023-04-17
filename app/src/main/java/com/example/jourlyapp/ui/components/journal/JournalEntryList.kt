package com.example.jourlyapp.ui.components.journal

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.ui.theme.Blue80
import com.example.jourlyapp.ui.theme.Danger80
import com.example.jourlyapp.ui.theme.Light
import com.example.jourlyapp.ui.theme.Margins
import com.example.jourlyapp.viewmodel.journal.JournalViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun JournalEntryList(
    modifier: Modifier = Modifier,
    journalEntries: List<JournalEntry>,
    viewModel: JournalViewModel,
    listState: LazyListState
) {
    val coroutineScope = rememberCoroutineScope()
    val toastContext = LocalContext.current

    Column(modifier = modifier) {
        Text("Your Timeline", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(Margins.vertical))
        LazyColumn(
            state = listState,
            modifier = Modifier.testTag("journalEntryList")
        ) {
            items(
                items = journalEntries,
                key = { it.id ?: 0 },
                itemContent = { journalEntry ->
                    var showDeleteDialog by remember {
                        mutableStateOf(false)
                    }
                    val dismissState = rememberDismissState(
                        confirmValueChange = {
                            if (it == DismissValue.DismissedToStart) {
                                showDeleteDialog = !showDeleteDialog
                                return@rememberDismissState true
                            } else if (it == DismissValue.DismissedToEnd) {
                                coroutineScope.launch {
                                    Toast.makeText(
                                        toastContext,
                                        "TODO: Show edit entry screen",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                return@rememberDismissState false
                            }
                            true
                        },
                        positionalThreshold = { totalDistance ->
                            // after 10% of total swipe distance the state change will be triggered
                            totalDistance / 10
                        }
                    )
                    if (showDeleteDialog) {
                        DeleteEntryDialog(
                            viewModel = viewModel,
                            currentEntry = journalEntry,
                            onDismiss = {
                                showDeleteDialog = false
                                coroutineScope.launch {
                                    dismissState.reset()
                                }
                            },
                            onConfirm = {
                                showDeleteDialog = false
                                coroutineScope.launch {
                                    dismissState.reset()
                                    Toast.makeText(
                                        toastContext,
                                        "Entry deleted",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                    }

                    SwipeToDismiss(
                        state = dismissState,
                        modifier = Modifier
                            .animateItemPlacement(),
                        background = {
                            val color by animateColorAsState(
                                targetValue = when (dismissState.targetValue) {
                                    DismissValue.Default -> Color.Transparent
                                    DismissValue.DismissedToStart -> Danger80
                                    DismissValue.DismissedToEnd -> Blue80
                                    else -> Color.Transparent
                                }
                            )
                            val iconScale by animateFloatAsState(
                                targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f
                                else 1.2f
                            )
                            val iconAlignment =
                                if (dismissState.dismissDirection == DismissDirection.StartToEnd)
                                    Alignment.CenterStart
                                else Alignment.CenterEnd

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(start = 12.dp, end = 12.dp),
                                contentAlignment = iconAlignment
                            ) {
                                if (dismissState.dismissDirection == DismissDirection.StartToEnd)
                                    Icon(
                                        Icons.Filled.Edit,
                                        contentDescription = "Edit Entry",
                                        modifier = Modifier.scale(iconScale),
                                        tint = Light
                                    )
                                if (dismissState.dismissDirection == DismissDirection.EndToStart)
                                    Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = "Delete Entry",
                                        modifier = Modifier.scale(iconScale),
                                        tint = Light
                                    )
                            }
                        },
                        directions = setOf(
                            DismissDirection.EndToStart,
                            DismissDirection.StartToEnd
                        ),
                        dismissContent = {
                            JournalEntryListItem(
                                journalEntry = journalEntry,
                                onClick = {
                                    //TODO: navigate to journal entry details screen
                                }
                            )
                        }
                    )
                }
            )
        }
    }
}
