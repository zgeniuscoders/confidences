package cd.zgeniuscoders.confidences.chat.presentation.chat_lists

import cd.zgeniuscoders.confidences.chat.domain.Message

data class ChatListState(
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
    val message: String = ""
)
