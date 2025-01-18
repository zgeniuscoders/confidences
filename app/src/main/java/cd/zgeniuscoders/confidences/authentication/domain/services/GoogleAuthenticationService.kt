package cd.zgeniuscoders.confidences.authentication.domain.services

import cd.zgeniuscoders.confidences.authentication.domain.models.AuthResponse
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import cd.zgeniuscoders.confidences.user.data.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface GoogleAuthenticationService {
    suspend fun signWithGoogle(): Flow<Result<AuthResponse>>
}