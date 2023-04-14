package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.ui.theme.Blue80
import com.example.jourlyapp.ui.theme.Danger80
import com.example.jourlyapp.ui.theme.Light
import com.example.jourlyapp.ui.util.DateTimeParser
import com.example.jourlyapp.viewmodel.journal.JournalViewModel

@Composable
fun JournalEntryListItem(
    modifier: Modifier = Modifier,
    journalEntry: JournalEntry,
    viewModel: JournalViewModel
) {
    /**
     * Variable to implement the state of the DropDownMenu
     */
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    /**
     * Variables to calculate the place where user clicked on a particular card and show the menu accordingly
     */
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var heightOffset by remember {
        mutableStateOf(0.dp)
    }
        val density = LocalDensity.current

    /**
     * Variable to implement interaction with the menu items
     */
    val interactionSource = remember {
        MutableInteractionSource()
    }

    /**
     * Variable to show the dialog for deleting an entry
     */
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .padding(vertical = 6.dp)
            .onSizeChanged { heightOffset = with(density) { it.height.toDp() } }
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = {
                        isContextMenuVisible = true
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                    },
                    onPress = {
                        val press = PressInteraction.Press(it)
                        interactionSource.emit(press)
                        tryAwaitRelease()
                        interactionSource.emit(PressInteraction.Release(press))
                    }
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            MoodIcon(
                mood = journalEntry.mood,
                modifier.align(CenterVertically)
            )
            Spacer(modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterVertically)
            ) {

                Text(
                    text = journalEntry.mood.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = DateTimeParser.toLongDateString(journalEntry.date),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },
            offset = pressOffset.copy(
                y = pressOffset.y - heightOffset
            )
        ) {
            DropdownMenuItem(
                text = { Text(text = "Modify", textAlign = TextAlign.Center) },
                modifier = Modifier.background(color = Blue80),
                leadingIcon = { Icon( Icons.Default.Edit, contentDescription = "Edit Icon")},
                colors = MenuDefaults.itemColors(
                    textColor = Light,
                    leadingIconColor = Light
                ),
                onClick = {
                    //TODO: implement the modify dialog
                    isContextMenuVisible = false
                }
            )
            DropdownMenuItem(
                text = { Text(text = "Delete", textAlign = TextAlign.Center) },
                modifier = Modifier.background(color = Danger80),
                leadingIcon = { Icon( Icons.Default.Delete, contentDescription = "Edit Icon")},
                colors = MenuDefaults.itemColors(
                    textColor = Light,
                    leadingIconColor = Light
                ),
                onClick = {
                    isContextMenuVisible = false
                    showDeleteDialog = true
                }
            )
        }
    }

    if(showDeleteDialog) {
        DeleteEntryDialog(viewModel = viewModel, currentEntry = journalEntry, onDismiss = {showDeleteDialog = false})
    }
}