package cd.zgeniuscoders.confidences.authentication.domain.services

import android.content.Intent

interface AuthenticationService {


    suspend fun signOut()

    suspend fun getSignInUser()


}