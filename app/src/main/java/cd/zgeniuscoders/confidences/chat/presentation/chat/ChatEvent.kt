package cd.zgeniuscoders.confidences.chat.presentation.chat

sealed interface ChatEvent {

    data class OnMessageFieldChange(val message: String) : ChatEvent

    data object OnSendMessageButtonClick : ChatEvent

    data class OnDeleteMessageForMe(val messageId: String): ChatEvent

    data class OnDeleteMessageForEveryOne(val messageId: String): ChatEvent

}