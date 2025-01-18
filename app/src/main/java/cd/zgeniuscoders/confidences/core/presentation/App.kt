package cd.zgeniuscoders.confidences.core.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import cd.zgeniuscoders.confidences.authentication.presentation.sign_google.SignWithGooglePage
import cd.zgeniuscoders.confidences.chat.presentation.chat.ChatPage
import cd.zgeniuscoders.confidences.chat.presentation.chat_lists.ChatListPage
import cd.zgeniuscoders.confidences.chat.presentation.contact_list.ContactListPage
import cd.zgeniuscoders.confidences.core.domain.utils.Routes
import cd.zgeniuscoders.confidences.user.presentation.OnBoardingPage
import cd.zgeniuscoders.confidences.user.presentation.profile.ProfilePage

@Composable
fun App(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    currentRoute: Routes?
) {

    NavHost(
        navController = navController,
        startDestination = Routes.AuthenticationNavGraph
    ) {

        navigation<Routes.MainNavGraph>(
            startDestination = Routes.ChatList,
        ) {

            composable<Routes.ChatList> {
                ChatListPage(
                    navHostController = navController,
                    snackbarHostState = snackbarHostState
                )
            }

            composable<Routes.Settings> {
                ProfilePage(
                    navController,
                    snackbarHostState
                )
            }

            composable<Routes.ContactList> {
                ContactListPage(
                    navHostController = navController,
                    snackbarHostState = snackbarHostState
                )
            }

        }

        composable<Routes.Chat> {
            ChatPage(navHostController = navController)
        }

        navigation<Routes.AuthenticationNavGraph>(startDestination = Routes.Authentication) {

            composable<Routes.OnBoarding> {
                OnBoardingPage(
                    navController,
                    snackbarHostState
                )
            }

            composable<Routes.Authentication> {
                SignWithGooglePage(
                    navController,
                    snackbarHostState
                )
            }

        }

    }
}