package cd.zgeniuscoders.confidences.authentication.data.mappers

import cd.zgeniuscoders.confidences.authentication.domain.models.AuthResponse
import cd.zgeniuscoders.confidences.authentication.domain.models.UserRequest

fun AuthResponse.toUserRequest(): UserRequest {
    return UserRequest(
        userId = data.userId,
        username = data.username!!,
        email = data.email!!,
        photoUrl = data.profilePictureUrl,
        phoneNumber = data.phoneNumber
    )
}