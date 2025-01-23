package cd.zgeniuscoders.confidences.chat.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.confidences.core.domain.utils.Routes

@Composable
fun UserItemCard(
    navHostController: NavHostController,
    hasAccount: Boolean,
    userId: String?,
    phoneNumber: String?,
    isFirst: Boolean = false,
    content: @Composable() (RowScope.() -> Unit
    )
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = hasAccount
            ) {

                navHostController.navigate(
                    Routes.Chat(
                        userId!!,
                        phoneNumber!!,
                        isFirst
                    )
                )

            }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            content()
        }
    }
}