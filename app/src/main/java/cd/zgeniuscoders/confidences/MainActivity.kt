package cd.zgeniuscoders.confidences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.confidences.authentication.presentation.sign_google.SignWithGooglePage
import cd.zgeniuscoders.confidences.chat.presentation.chat.ChatPage
import cd.zgeniuscoders.confidences.chat.presentation.chat_lists.ChatListPage
import cd.zgeniuscoders.confidences.chat.presentation.contact_list.ContactListPage
import cd.zgeniuscoders.confidences.core.domain.extension.fromRoute
import cd.zgeniuscoders.confidences.core.domain.utils.Routes
import cd.zgeniuscoders.confidences.ui.theme.ConfidencesTheme
import cd.zgeniuscoders.confidences.user.presentation.OnBoardingPage

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConfidencesTheme {

                val navController = rememberNavController()
                val snackbarHostState = SnackbarHostState()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.fromRoute()

                installSplashScreen()

                NavHost(
                    navController = navController,
                    startDestination = Routes.MainNavGraph
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

                        composable<Routes.UserProfile> {

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
        }
    }
}

