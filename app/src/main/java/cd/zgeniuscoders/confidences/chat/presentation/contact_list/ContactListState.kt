package cd.zgeniuscoders.confidences.chat.presentation.contact_list

import cd.zgeniuscoders.confidences.chat.domain.models.Contact
import cd.zgeniuscoders.confidences.user.domain.models.User

data class ContactListState(
    val contacts: List<Contact> = emptyList(),
    val users: List<User> = emptyList(),
    val message: String = "",
    val isLoading: Boolean = false
)