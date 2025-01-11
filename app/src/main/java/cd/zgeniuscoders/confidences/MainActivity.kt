package cd.zgeniuscoders.confidences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddComment
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.confidences.authentication.presentation.sign_google.SignWithGooglePage
import cd.zgeniuscoders.confidences.chat.presentation.chat_lists.ChatListPage
import cd.zgeniuscoders.confidences.core.domain.extension.fromRoute
import cd.zgeniuscoders.confidences.core.domain.utils.Routes
import cd.zgeniuscoders.confidences.ui.theme.ConfidencesTheme
import cd.zgeniuscoders.confidences.user.presentation.OnBoardingPage

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
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

                Scaffold(
                    topBar = {
                        if (currentRoute == Routes.ChatList) {
                            TopAppBar(
                                title = { Text(text = "Confidences") },
                                actions = {
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            Icons.Rounded.Search,
                                            contentDescription = "search_icon"
                                        )
                                    }
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            Icons.Rounded.Person,
                                            contentDescription = "search_icon"
                                        )
                                    }
                                }
                            )
                        }
                    },
                    floatingActionButton = {
                        if (currentRoute == Routes.ChatList) {
                            FloatingActionButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Rounded.AddComment,
                                    contentDescription = "add_chat_icon"
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    NavHost(
                        modifier = Modifier.padding(innerPadding),
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

