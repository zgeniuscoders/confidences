package cd.zgeniuscoders.confidences.user.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.confidences.chat.presentation.components.AvatarCard
import cd.zgeniuscoders.confidences.user.presentation.profile.ProfileState

@Composable
fun UserProfileItem(state: ProfileState) {
    if (state.user != null) {
        Card {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {

                    Text(
                        text = state.user.username,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = state.user.phoneNumber,
                        color = MaterialTheme.colorScheme.secondary
                    )

                }
                AvatarCard(initialLetter = 'Z')
            }
        }
    }
}