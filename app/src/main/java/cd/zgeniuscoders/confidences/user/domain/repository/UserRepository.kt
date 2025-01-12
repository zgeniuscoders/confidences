package cd.zgeniuscoders.confidences.user.domain.repository

import cd.zgeniuscoders.confidences.authentication.domain.models.UserRequest
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import cd.zgeniuscoders.confidences.user.data.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun addUser(request: UserRequest): Flow<Result<Boolean>>

    suspend fun updateUser(): Flow<Result<Boolean>>

    suspend fun getUserById(receiverId: String): Flow<Result<UserDto>>

}