package com.example.jourlyapp.ui.components.journal

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.ui.theme.Blue80
import com.example.jourlyapp.ui.theme.Danger80
import com.example.jourlyapp.ui.theme.Light
import com.example.jourlyapp.ui.theme.Margins
import com.example.jourlyapp.viewmodel.journal.JournalViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun JournalEntryListSwipe(
    modifier: Modifier = Modifier,
    journalEntries: List<JournalEntry>,
    viewModel: JournalViewModel
) {
    Column(modifier = modifier) {
        Text("Your Timeline", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(Margins.vertical))
        LazyColumn {
            items(items = journalEntries,
                key = { it.id!! }, //here I am using the casting from nullable to non-nullable!
                itemContent = {item ->
                    var showDeleteDialog by remember {
                        mutableStateOf(false)
                    }

                    val dismissState = rememberDismissState(
                        confirmValueChange = {
                            //TODO: implement a dialog for item removal
                            if (it == DismissValue.DismissedToStart) {
                                showDeleteDialog = !showDeleteDialog
                            }
                            true
                        }
                    )

                    if (showDeleteDialog) {
                        DeleteEntryDialog(
                            viewModel = viewModel,
                            currentEntry = item,
                            onDismiss = { showDeleteDialog = false}
                        )
                    }

                    if (dismissState.currentValue != DismissValue.Default) {
                        LaunchedEffect(Unit) {
                            dismissState.reset()
                        }
                    }

                    SwipeToDismiss(
                        state = dismissState,
                        modifier = Modifier
                            .animateItemPlacement(),
                        background = {
                            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                            val color by animateColorAsState(
                                targetValue = when (dismissState.targetValue) {
                                    DismissValue.Default -> Blue80
                                    DismissValue.DismissedToStart -> Danger80
                                    else -> Color.Transparent
                                }
                            )

                            val icon = Icons.Default.Delete

                            val scale by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)

                            val alignment = Alignment.CenterEnd

                            //TODO: ask for approval on the background box, both color and shape
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(start = 12.dp, end = 12.dp),
                                contentAlignment = alignment
                            ) {
                                Icon(icon, contentDescription = "Icon", modifier = Modifier.scale(scale), tint = Light)
                            }
                        },
                        directions = setOf(DismissDirection.EndToStart),
                        dismissContent = {
                            JournalEntryListItemSwipe(
                                journalEntry = item,
                                onClick = {
                                    //TODO: implement the modify dialog
                                }
                            )
                        }
                    )
                }
            )
        }
    }
}
