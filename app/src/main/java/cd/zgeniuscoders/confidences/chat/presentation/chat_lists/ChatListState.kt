package cd.zgeniuscoders.confidences.chat.presentation.chat_lists

import cd.zgeniuscoders.confidences.chat.domain.models.LatestMessage

data class ChatListState(
    val isLoading: Boolean = false,
    val messages: List<LatestMessage> = emptyList(),
    val message: String = ""
)
