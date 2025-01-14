package cd.zgeniuscoders.confidences.chat.presentation.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEmotions
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
        }
    ) { innerP ->
        ConstraintLayout(
            modifier = Modifier
                .padding(innerP)
                .fillMaxSize()
        ) {
            val (chatListsBox, bottomBox) = createRefs()

            LazyColumn(
                modifier = Modifier
                    .constrainAs(chatListsBox) {
                        top.linkTo(parent.top)
                        bottom.linkTo(bottomBox.top)
                    }
                    .padding(vertical = 40.dp)
                    .fillMaxSize()
            ) {
                items(state.messages) { message ->
                    ChatItem(message = message, currentUserId = state.currentUserId)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(bottomBox) {
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TextField(
                    leadingIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Rounded.EmojiEmotions,
                                contentDescription = "emoji_icon"
                            )
                        }
                    },
                    value = state.message,
                    onValueChange = {
                        onEvent(ChatEvent.OnMessageFieldChange(it))
                    },
                    maxLines = 3,
                    modifier = Modifier
                        .weight(0.8f)
                        .height(55.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Card(
                    modifier = Modifier.size(55.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = { onEvent(ChatEvent.OnSendMessageButtonClick) }) {
                            Icon(
                                imageVector = Icons.Rounded.Send,
                                contentDescription = "emoji_icon"
                            )
                        }
                    }
                }
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
    message = "Petit nanga",
    senderId = "1",
    image = null,
    timestamp = 1003000
)