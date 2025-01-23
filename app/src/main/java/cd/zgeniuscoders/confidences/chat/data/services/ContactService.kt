package cd.zgeniuscoders.confidences.chat.data.services

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.update


data class ContactDeviceModel(
    val name: String = "",
    val number: String = ""
)

class ContactService(
    private val context: Context
) {

    private val contentResolver by lazy {
        context.contentResolver
    }

    private val _contactLists = MutableStateFlow<List<ContactDeviceModel>>(emptyList())
    val contactLists: StateFlow<List<ContactDeviceModel>>
        get() = _contactLists.asStateFlow()

    fun fetchContact(): Flow<Result<Boolean>> = callbackFlow {

        try {
            val cursor: Cursor? = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )

            cursor?.use {
                val nameIndex =
                    it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                while (it.moveToNext()) {
                    val name = it.getString(nameIndex)
                    val phone = it.getString(numberIndex)

                    _contactLists.update { contacts ->
                        run {
                            val contact = ContactDeviceModel(name, phone)
                            if (contact in contacts) contacts else contacts + contact
                        }
                    }
                }
            }

            trySend(
                Result.Success(
                    true
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            trySend(
                Result.Error(
                    message = e.message.toString()
                )
            )
        }

        awaitClose()

    }

}