package cd.zgeniuscoders.confidences.user.presentation.profile

sealed interface ProfileEvent {

    data object OnLogoutButtonClick: ProfileEvent

}