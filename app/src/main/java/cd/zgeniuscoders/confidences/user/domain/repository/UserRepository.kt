package cd.zgeniuscoders.confidences.user.domain.repository

import cd.zgeniuscoders.confidences.authentication.domain.models.UserRequest
import cd.zgeniuscoders.confidences.chat.data.services.ContactService
import cd.zgeniuscoders.confidences.chat.domain.models.Contact
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import cd.zgeniuscoders.confidences.user.data.dto.UserDto
import cd.zgeniuscoders.confidences.user.data.dto.UsersDto
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun hasAccount(userId: String): Flow<Result<Boolean>>

    suspend fun addUser(request: UserRequest): Flow<Result<Boolean>>

    suspend fun updateUser(): Flow<Result<Boolean>>

    suspend fun getUserById(userId: String): Flow<Result<UserDto>>

    suspend fun getUsersByPhoneNumber(phoneNumbers: List<Contact>): Flow<Result<UsersDto>>

}