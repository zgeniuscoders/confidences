package cd.zgeniuscoders.confidences.chat.presentation.chat_lists

import cd.zgeniuscoders.confidences.chat.domain.models.Contact
import cd.zgeniuscoders.confidences.chat.domain.models.LatestMessage
import cd.zgeniuscoders.confidences.user.domain.models.User

data class ChatListState(
    val isLoading: Boolean = true,
    val messages: List<LatestMessage> = emptyList(),
    val filterMessages: List<LatestMessage> = emptyList(),
    val contacts: List<Contact> = emptyList(),
    val users: List<User> = emptyList(),
    val categories: List<String> = listOf("Tout", "Mes Contacts", "Anonyme"),
    val selectedCategory: String = "Tout",
    val message: String = "",
    val currentUser: User? = null
)
