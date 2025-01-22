package cd.zgeniuscoders.confidences.chat.presentation.chat_lists

sealed interface ChatListEvent {

    data class OnSelectedCategoryChange(val category: String) : ChatListEvent

}