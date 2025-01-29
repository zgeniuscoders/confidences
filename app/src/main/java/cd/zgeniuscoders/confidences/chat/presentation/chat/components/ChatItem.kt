package cd.zgeniuscoders.confidences.chat.presentation.chat.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.confidences.chat.domain.models.Message
import cd.zgeniuscoders.confidences.chat.presentation.chat.ChatEvent
import dev.jeziellago.compose.markdowntext.MarkdownText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatItem(message: Message, currentUserId: String, onEvent: (event: ChatEvent) -> Unit) {

    var showTime by remember {
        mutableStateOf(false)
    }

    var showDeleteForMeDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 2.dp, horizontal = 10.dp),
        horizontalAlignment = if (message.senderId == currentUserId) Alignment.End else Alignment.Start
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = {
                            showDeleteForMeDialog = true
                        },
                        onTap = {
                            showTime = !showTime
                        }
                    )
                },
            colors = CardDefaults.cardColors(
                containerColor = if (message.senderId == currentUserId)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.surfaceContainerHigh
            )
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
            ) {
                MarkdownText(markdown = message.message)
            }
        }


        if (showTime) {
            Text(
                message.timestamp,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        }

        if (showDeleteForMeDialog) {

            AlertDialog(
                onDismissRequest = { showDeleteForMeDialog = false },
                dismissButton = {
                    OutlinedButton(onClick = { showDeleteForMeDialog = false }) {
                        Text("Annuler")
                    }
                },
                confirmButton = {

                    if (message.senderId == currentUserId) {

                        OutlinedButton(
                            onClick = {
                                onEvent(
                                    ChatEvent.OnDeleteMessageForEveryOne(
                                        message.id
                                    )
                                )
                            }) {
                            Text("Supprimer pour tout le monde")
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            onEvent(
                                ChatEvent.OnDeleteMessageForMe(
                                    message.id
                                )
                            )
                        }) {
                        Text("Supprimer pour moi")
                    }

                },
                title = {
                    Text(
                        text = "Supprimer le message",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )
        }

    }

}

