package cd.zgeniuscoders.confidences.user.data.mappers

import cd.zgeniuscoders.confidences.user.data.dto.UserDto
import cd.zgeniuscoders.confidences.user.domain.models.User

fun UserDto.toUserModel(): User {
    return User(
        userId = data.userId,
        username = data.username,
        email = data.email,
        phoneNumber = data.phoneNumber,
        profilePictureUrl = data.profilePictureUrl
    )
}