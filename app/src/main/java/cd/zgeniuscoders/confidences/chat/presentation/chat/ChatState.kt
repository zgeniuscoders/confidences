package cd.zgeniuscoders.confidences.chat.presentation.chat

import cd.zgeniuscoders.confidences.chat.domain.models.Message
import cd.zgeniuscoders.confidences.user.domain.models.User

data class ChatState(
    val messages: List<Message> = emptyList(),
    val user: User? = null,
    val isLoading: Boolean = false,
    val message: String = "",
    val currentUserId: String = "",
    val senderRoom: String = "",
    val receiverRoom: String = "",
    val error: String = ""
)
