package cd.zgeniuscoders.confidences.chat.data.mappers

import cd.zgeniuscoders.confidences.chat.data.dto.ContactDto
import cd.zgeniuscoders.confidences.chat.data.dto.ContactsDto
import cd.zgeniuscoders.confidences.chat.data.services.ContactDeviceModel
import cd.zgeniuscoders.confidences.chat.domain.models.Contact


fun ContactsDto.toContactList(): List<Contact> {
    return data.map {
        Contact(
            id = it.id,
            name = it.name,
            email = it.email,
            numberPhone = it.numberPhone
        )
    }
}

fun ContactDto.toContactModel(): Contact {
    return Contact(
        id = data.id,
        name = data.name,
        email = data.email,
        numberPhone = data.numberPhone
    )

}

fun List<ContactDeviceModel>.toContactList(): List<Contact> {
    return map {
        Contact(
            name = it.name,
            numberPhone = it.number,
            email = ""
        )
    }
}