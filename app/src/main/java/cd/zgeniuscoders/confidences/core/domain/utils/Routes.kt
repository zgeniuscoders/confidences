package cd.zgeniuscoders.confidences.core.domain.utils

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object Authentication : Routes

    @Serializable
    data object AuthenticationNavGraph : Routes

    @Serializable
    data object OnBoarding : Routes

    @Serializable
    data object MainNavGraph : Routes

    @Serializable
    data object ChatList : Routes

    @Serializable
    data object ContactList : Routes

    @Serializable
    data object Settings : Routes

    @Serializable
    data class Chat(
        val userId: String,
        val phoneNumber: String,
        val isFirst: Boolean = false
    ) : Routes

    @Serializable
    data class UserProfile(
        val userId: String
    ) : Routes

}