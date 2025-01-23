package cd.zgeniuscoders.confidences.core.domain.extension

import androidx.navigation.NavBackStackEntry
import cd.zgeniuscoders.confidences.core.domain.utils.Routes

fun NavBackStackEntry?.fromRoute(): Routes {
    this?.destination?.route?.substringBefore("?")?.substringBefore("/")
        ?.substringAfterLast(".")?.let {
            when (it) {
                Routes.ChatList::class.simpleName -> return Routes.ChatList
                Routes.Chat::class.simpleName -> return Routes.Chat("", "")
                Routes.Authentication::class.simpleName -> return Routes.Authentication
                Routes.OnBoarding::class.simpleName -> return Routes.OnBoarding
                Routes.UserProfile::class.simpleName -> return Routes.UserProfile("")
                Routes.ContactList::class.simpleName -> return Routes.ContactList
                else -> return Routes.ChatList
            }
        }
    return Routes.ChatList
}
