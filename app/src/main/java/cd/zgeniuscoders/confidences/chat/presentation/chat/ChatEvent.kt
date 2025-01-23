package cd.zgeniuscoders.confidences.chat.presentation.chat

sealed interface ChatEvent {

    data class OnMessageFieldChange(val message: String) : ChatEvent

    data object OnSendMessageButtonClick : ChatEvent

}