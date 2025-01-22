package cd.zgeniuscoders.confidences.chat.presentation.chat_lists

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddComment
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.confidences.R
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
                    IconButton(onClick = {
                        navHostController.navigate(Routes.Settings)
                    }) {
                        Icon(
                            Icons.Rounded.Person,
                            contentDescription = "parametre"
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

        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.messages.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(250.dp),
                        painter = painterResource(id = R.drawable.empty_message),
                        contentDescription = null
                    )
                    Text(
                        text = "Pour commnecer la discussion, veuillez appuyer sur le bouton Contact",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                ) {
                    items(state.messages) { message ->

                        val user = state.users.find { it.userId == message.receiverId }
                        var phoneNumber = user?.phoneNumber
                        var username = user?.username

                        if (user != null && state.currentUser != null) {

                            val currentUser = state.currentUser

                            phoneNumber = if (message.room == user.userId) {
                                currentUser.phoneNumber
                            } else {
                                user.phoneNumber
                            }

                            username = if (message.room == currentUser.userId) {

                                val contactUser =
                                    state.contacts.find { it.numberPhone == user.phoneNumber }

                                contactUser!!.name

                            } else {
                                user.username
                            }

                        }

                        UserItemCard(
                            navHostController = navHostController,
                            hasAccount = true,
                            userId = message.receiverId,
                            phoneNumber = phoneNumber
                        ) {

                            AvatarCard(
                                initialLetter = if (username != null) username[0] else 'Z',
                                size = 50.dp
                            )

                            Column {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        username ?: "unknown",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = message.timestamp,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                Text(
                                    if (message.message.length >= 34)
                                        message.message.substring(0, 34) + "..."
                                    else
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
    message = "Petit nanga oza bien ? po nga naza nanga bien osalani ",
    receiverId = "1",
    image = null,
    timestamp = "12/12/2025",
    room = "1"
)