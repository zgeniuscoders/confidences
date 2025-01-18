package cd.zgeniuscoders.confidences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.confidences.core.domain.extension.fromRoute
import cd.zgeniuscoders.confidences.core.domain.utils.Routes
import cd.zgeniuscoders.confidences.core.presentation.App
import cd.zgeniuscoders.confidences.core.presentation.AppViewModel
import cd.zgeniuscoders.confidences.ui.theme.ConfidencesTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        var keepConditionScreen = true

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            keepConditionScreen
        }

        enableEdgeToEdge()
        setContent {
            ConfidencesTheme {

                val navController = rememberNavController()
                val snackbarHostState = SnackbarHostState()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.fromRoute()

                val vm = koinViewModel<AppViewModel>()
                val state by vm.state.collectAsStateWithLifecycle()

                Surface {
                    App(
                        navController,
                        snackbarHostState,
                        currentRoute
                    )

                }

                LaunchedEffect(state.isLogged) {

                    if (!state.isLogged) {
                        navController.navigate(Routes.AuthenticationNavGraph) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }

                    keepConditionScreen = false
                }

            }
        }
    }
}

