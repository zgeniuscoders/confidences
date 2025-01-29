package cd.zgeniuscoders.confidences.chat.presentation.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.confidences.R
import cd.zgeniuscoders.confidences.chat.domain.models.Message
import cd.zgeniuscoders.confidences.chat.presentation.chat.components.ChatItem
import cd.zgeniuscoders.confidences.chat.presentation.chat.components.ChatTopBar
import cd.zgeniuscoders.confidences.chat.presentation.contact_list.user
import cd.zgeniuscoders.confidences.ui.theme.ConfidencesTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatPage(
    navHostController: NavHostController
) {
    val vm = koinViewModel<ChatViewModel>()
    val state by vm.state.collectAsStateWithLifecycle()

    val onEvent = vm::onTriggerEvent

    ChatBody(navHostController, state, onEvent)
}

@Composable
fun ChatBody(
    navHostController: NavHostController,
    state: ChatState,
    onEvent: (event: ChatEvent) -> Unit
) {
    Scaffold(
        topBar = {
            ChatTopBar(navHostController = navHostController, state.user)
        }, bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextField(
                    modifier = Modifier
                        .weight(0.8f)
                        .heightIn(min = 50.dp, max = 120.dp),
                    trailingIcon = {
                        IconButton(
                            onClick = { onEvent(ChatEvent.OnSendMessageButtonClick) },
                            enabled = state.message.isNotEmpty()
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Send,
                                contentDescription = "send icon"
                            )
                        }
                    },
                    value = state.message,
                    onValueChange = {
                        onEvent(ChatEvent.OnMessageFieldChange(it))
                    },
                    maxLines = 4,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

            }
        }
    ) { innerP ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.chat_bg),
                contentDescription = "arriere plan chat"
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(innerP)
                .fillMaxSize()
        ) {

            items(state.messages) { message ->
                ChatItem(
                    message = message,
                    currentUserId = state.currentUserId,
                    onEvent
                )
            }
        }

    }

}

@PreviewLightDark
@Composable
fun ChatPagePreview(modifier: Modifier = Modifier) {
    ConfidencesTheme {

        ChatBody(
            rememberNavController(),
            state = ChatState(
                currentUserId = "1",
                user = user,
                messages = (1..5).map {
                    message.copy(senderId = (1..2).random().toString())
                }
            )) {

            }

    }
}

internal val message = Message(
    "1",
    message = "Petit nanga hfkfffhkkhfkfkfkfkfkfkfkfhkfkfkhfkfkffkjfkfkfkfkfkfjfkfjfkfkjfkjfjkfjk",
    senderId = "1",
    image = null,
    timestamp = 121133131333,
    timeAgo = "12/12/1940",
    isRead = true
)