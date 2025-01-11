package cd.zgeniuscoders.confidences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.confidences.authentication.presentation.sign_google.SignWithGooglePage
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

                installSplashScreen()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Routes.AuthenticationNavGraph
                    ) {

                        navigation<Routes.MainNavGraph>(
                            startDestination = Routes.ChatList,
                        ) {

                            composable<Routes.ChatList> {
                                Text("Chat list")
                            }

                            composable<Routes.Chat> {

                            }

                            composable<Routes.UserProfile> {

                            }

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
}

