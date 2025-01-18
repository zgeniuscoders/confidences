package cd.zgeniuscoders.confidences.chat.presentation.contact_list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.confidences.chat.domain.models.Contact
import cd.zgeniuscoders.confidences.chat.presentation.components.AvatarCard
import cd.zgeniuscoders.confidences.chat.presentation.components.UserItemCard
import cd.zgeniuscoders.confidences.ui.theme.ConfidencesTheme
import cd.zgeniuscoders.confidences.user.domain.models.User
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContactListPage(
    navHostController: NavHostController, snackbarHostState: SnackbarHostState
) {
    val vm = koinViewModel<ContactListViewModel>()
    val state by vm.state.collectAsState()

    val onEvent = vm::onTriggerEvent

    LaunchedEffect(state.message) {
        if (state.message.isNotBlank()) {
            snackbarHostState.showSnackbar(state.message)
        }
    }

    ContactListPageBody(navHostController, state, onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListPageBody(
    navHostController: NavHostController,
    state: ContactListState, onEvent: (event: ContactListEvent) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = "button retour"
                        )
                    }
                },
                title = { Text(text = "Mes contacts") },
            )
        }
    ) { innerP ->
        LazyColumn(
            modifier = Modifier.padding(innerP),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.contacts) { contact ->
                val user = state.users.find { it.phoneNumber == contact.numberPhone }

                UserItemCard(
                    navHostController = navHostController,
                    hasAccount = user != null,
                    userId = user?.userId
                ) {

                    AvatarCard(initialLetter = contact.name[0])
                    Column {
                        Text(
                            contact.name, style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            contact.numberPhone,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }


                    if (user != null) {
                        Spacer(modifier = Modifier.weight(1f))

                        Card(
                            modifier = Modifier.size(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiary
                            )
                        ) {

                        }
                    }


                }
            }
        }
    }

}

@PreviewLightDark
@Composable
fun ContactListPagePreview(modifier: Modifier = Modifier) {
    ConfidencesTheme {
        Scaffold { innerP ->
            ContactListPageBody(
                rememberNavController(),
                ContactListState(
                    contacts = (1..20).map { contact.copy(id = it.toString()) },
                    users = (1..20).map { user }
                )
            ) {

            }
        }
    }
}

internal val contact = Contact("1", "zgeniuscoders", "zgeniuscoders@gmail.com", "+2438268610723")
internal val user = User("1", "+2438268610723", "z", "", "")