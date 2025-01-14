package cd.zgeniuscoders.confidences.chat.presentation.chat_lists

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddComment
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.confidences.chat.domain.models.LatestMessage
import cd.zgeniuscoders.confidences.chat.presentation.components.AvatarCard
import cd.zgeniuscoders.confidences.chat.presentation.components.UserItemCard
import cd.zgeniuscoders.confidences.core.domain.utils.Routes
import cd.zgeniuscoders.confidences.ui.theme.ConfidencesTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatListPage(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState
) {

    val vm = koinViewModel<ChatListViewModel>()
    val state by vm.state.collectAsStateWithLifecycle()
    val onEvent = vm::onTriggerEvent

    LaunchedEffect(state.message) {
        if (state.message.isNotBlank()) {
            snackbarHostState.showSnackbar(state.message)
        }
    }

    ChatListBody(navHostController, state, onEvent)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListBody(
    navHostController: NavHostController,
    state: ChatListState, onEvent: (event: ChatListEvent) -> Unit
) {
    val contactLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if (isGranted) {
                    navHostController.navigate(Routes.ContactList)
                }
            })

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Confidences") },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Rounded.Search,
                            contentDescription = "search_icon"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Rounded.Person,
                            contentDescription = "search_icon"
                        )
                    }
                }
            )

        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                contactLauncher.launch(Manifest.permission.READ_CONTACTS)
            }) {
                Icon(
                    imageVector = Icons.Rounded.AddComment,
                    contentDescription = "add_chat_icon"
                )

            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.messages) { message ->
                UserItemCard(
                    navHostController = navHostController,
                    hasAccount = true,
                    userId = message.receiverId
                ) {
                    AvatarCard(initialLetter = 'A')

                    Column {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "zgeniuscoders", style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "20:00",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Text(
                            message.message,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                }

            }
        }

    }

}

@PreviewLightDark
@Composable
fun ChatListPreview(modifier: Modifier = Modifier) {
    ConfidencesTheme {

        ChatListBody(
            rememberNavController(),
            state = ChatListState(
                messages = (1..10).map { lastMessage }
            )
        ) {

        }

    }
}

internal val lastMessage = LatestMessage(
    "1",
    message = "Petit nanga",
    receiverId = "1",
    image = null,
    timestamp = 1003000
)