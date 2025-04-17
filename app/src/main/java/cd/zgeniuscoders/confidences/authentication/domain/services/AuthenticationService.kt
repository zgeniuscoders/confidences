package cd.zgeniuscoders.confidences.authentication.domain.services

import android.content.Intent
import cd.zgeniuscoders.confidences.authentication.domain.models.AuthResponse
import cd.zgeniuscoders.confidences.authentication.domain.models.Login
import cd.zgeniuscoders.confidences.authentication.domain.models.Register
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import cd.zgeniuscoders.confidences.user.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthenticationService {


    suspend fun signOut(): Flow<Result<Boolean>>

    suspend fun login(data: Login): Flow<Result<AuthResponse>>

    suspend fun register(data: Register): Flow<Result<AuthResponse>>

    suspend fun getSignInUser()


}