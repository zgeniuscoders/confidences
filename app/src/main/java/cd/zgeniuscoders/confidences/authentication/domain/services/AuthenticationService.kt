package cd.zgeniuscoders.confidences.authentication.domain.services

import android.content.Intent
import cd.zgeniuscoders.confidences.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface AuthenticationService {


    suspend fun signOut(): Flow<Result<Boolean>>

    suspend fun getSignInUser()


}