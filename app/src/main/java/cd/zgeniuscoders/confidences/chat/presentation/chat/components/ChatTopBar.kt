package cd.zgeniuscoders.confidences.chat.presentation.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.confidences.chat.presentation.components.AvatarCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(navHostController: NavHostController, receiverUsername: String) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = "ic_back"
                )
            }
        },
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AvatarCard(initialLetter = receiverUsername[0], size = 40.dp)
                Column {
                    Text(
                        receiverUsername, style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        "Enligne",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "ic_more_vert"
                )
            }
        }
    )
}