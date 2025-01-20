package cd.zgeniuscoders.confidences.chat.presentation.chat_lists

import cd.zgeniuscoders.confidences.chat.domain.models.LatestMessage
import cd.zgeniuscoders.confidences.user.domain.models.User

data class ChatListState(
    val isLoading: Boolean = false,
    val messages: List<LatestMessage> = emptyList(),
    val users: List<User> = emptyList(),
    val message: String = ""
)
