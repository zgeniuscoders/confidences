package cd.zgeniuscoders.confidences.authentication.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.confidences.core.domain.utils.Routes
import cd.zgeniuscoders.confidences.ui.theme.ConfidencesTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginPage(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    val vm = koinViewModel<LoginViewModel>()
    val state = vm.state
    val onEvent = vm::onTriggerEvent


    LaunchedEffect(state.message) {
        if (state.message.isNotBlank()) {
            snackbarHostState.showSnackbar(state.message)
        }
    }

    LaunchedEffect(key1 = state.canPass) {

        if (state.canPass) {
            if (state.isLogged && state.hasAccount) {
                navController.navigate(Routes.MainNavGraph)
            } else {
                navController.navigate(Routes.OnBoarding)
            }
        }

    }

    LoginBody(
        onEvent,
        state,
        snackbarHostState,
        navController
    )
}

@Composable
fun LoginBody(
    onEvent: (event: LoginEvent) -> Unit,
    state: LoginState,
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerP ->
        Column(
            modifier = Modifier
                .padding(vertical = innerP.calculateTopPadding())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text("Connexion", fontSize = 24.sp)
            Box(modifier = Modifier.height(10.dp))
            TextField(
                value = state.email,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Email")
                },
                onValueChange = {
                    onEvent(LoginEvent.OnEmailChange(it))
                }
            )
            TextField(
                value = state.password,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Mot de passe")
                },
                onValueChange = {
                    onEvent(LoginEvent.OnPasswordChange(it))
                }
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(LoginEvent.OnSubmit)
                }
            ) {
                Text("Se connecter")
            }

            Text("cree un compte", Modifier.clickable(onClick = {
                navController.navigate(Routes.Register)
            }))
        }
    }

}

@PreviewLightDark
@Composable
fun LoginPreview(modifier: Modifier = Modifier) {
    ConfidencesTheme {
        LoginBody(
            state = LoginState(),
            onEvent = {},
            snackbarHostState = SnackbarHostState(),
            navController = rememberNavController()
        )
    }
}
