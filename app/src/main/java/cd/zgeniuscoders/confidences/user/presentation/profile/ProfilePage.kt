package cd.zgeniuscoders.confidences.user.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PersonOff
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.confidences.core.domain.utils.Routes
import cd.zgeniuscoders.confidences.ui.theme.ConfidencesTheme
import cd.zgeniuscoders.confidences.user.presentation.profile.components.SettingItem
import cd.zgeniuscoders.confidences.user.presentation.profile.components.UserProfileItem

@Composable
fun ProfilePage(navController: NavHostController, snackbarHostState: SnackbarHostState) {

}

@Composable
fun ProfilePageBody() {

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

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            UserProfileItem()
        }
        items(settingsItems) { item ->
            SettingItem(item)
        }
    }
}


@PreviewLightDark
@Composable
fun ProfilePagePreview() {
    ConfidencesTheme {
        Scaffold { innerP ->
            ProfilePageBody()
        }
    }
}

data class SettingItemModel(
    val title: String,
    val subTitle: String,
    val icon: ImageVector,
    val route: Routes
)