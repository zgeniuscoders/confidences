package cd.zgeniuscoders.confidences.core.domain.utils

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object Authentication : Routes

    @Serializable
    data object OnBoarding : Routes

    @Serializable
    data object MainNavGraph : Routes

    @Serializable
    data object ChatList : Routes

    @Serializable
    data class Chat(
        val userId: String
    ) : Routes

    @Serializable
    data class UserProfile(
        val userId: String
    ) : Routes

}