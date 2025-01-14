package cd.zgeniuscoders.confidences.chat.presentation.chat.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.confidences.chat.domain.models.Message

@Composable
fun ChatItem(message: Message, currentUserId: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = if (message.senderId == currentUserId) Alignment.End else Alignment.Start
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f),
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
                Text(
                    message.message,
                )
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        "${message.timestamp}",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}