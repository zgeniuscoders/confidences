package cd.zgeniuscoders.confidences.user.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PersonOff
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.confidences.core.domain.utils.Routes
import cd.zgeniuscoders.confidences.ui.theme.ConfidencesTheme
import cd.zgeniuscoders.confidences.user.presentation.profile.components.SettingItem
import cd.zgeniuscoders.confidences.user.presentation.profile.components.UserProfileItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfilePage(navController: NavHostController, snackbarHostState: SnackbarHostState) {

    val vm = koinViewModel<ProfileViewModel>()
    val state = vm.state
    val onEvent = vm::onTriggerEvent

    LaunchedEffect(state.message) {
        if (state.message.isNotBlank()) {
            snackbarHostState.showSnackbar(state.message)
        }
    }

    LaunchedEffect(state.isLogout) {
        if (state.isLogout) {
            navController.navigate(Routes.AuthenticationNavGraph)
        }
    }

    ProfilePageBody(state, onEvent)
}

@Composable
fun ProfilePageBody(state: ProfileState, onEvent: (event: ProfileEvent) -> Unit) {

    val settingsItems = listOf(
        SettingItemModel(
            "Utilisateur",
            "Changer mon nom d'utilisateur",
            Icons.Rounded.Person,
            Routes.MainNavGraph
        ),
        SettingItemModel(
            "Telephone",
            "Changer mon numero de telephone",
            Icons.Rounded.PhoneAndroid,
            Routes.MainNavGraph
        ), SettingItemModel(
            "Contact bloqués",
            "Changer mon numero de telephone",
            Icons.Rounded.PersonOff,
            Routes.MainNavGraph
        )
    )

    Scaffold { innerP ->
        LazyColumn(
            modifier = Modifier.padding(innerP),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                UserProfileItem()
            }
            items(settingsItems) { item ->
                SettingItem(item)
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onEvent(ProfileEvent.OnLogoutButtonClick)
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Box(
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Icon(
                                    modifier = Modifier.size(30.dp),
                                    imageVector = Icons.Rounded.Logout,
                                    contentDescription = ""
                                )
                            }
                        }
                        Column {
                            Text(
                                "Se deconnecter",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}


@PreviewLightDark
@Composable
fun ProfilePagePreview() {
    ConfidencesTheme {
        ProfilePageBody(state = ProfileState(), onEvent = {})
    }
}

data class SettingItemModel(
    val title: String,
    val subTitle: String,
    val icon: ImageVector,
    val route: Routes
)