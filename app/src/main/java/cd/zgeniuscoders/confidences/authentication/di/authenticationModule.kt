package cd.zgeniuscoders.confidences.authentication.di

import cd.zgeniuscoders.confidences.authentication.data.services.FirebaseAuthenticationServiceImpl
import cd.zgeniuscoders.confidences.authentication.data.services.GoogleAuthenticationServiceImpl
import cd.zgeniuscoders.confidences.authentication.domain.services.AuthenticationService
import cd.zgeniuscoders.confidences.authentication.domain.services.GoogleAuthenticationService
import cd.zgeniuscoders.confidences.authentication.presentation.sign_google.SignWithGoogleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authenticationModule = module {

    single<GoogleAuthenticationService> {
        val appContext = androidContext()
        GoogleAuthenticationServiceImpl(appContext)
    }

    single<AuthenticationService> {
        FirebaseAuthenticationServiceImpl(get())
    }

    viewModelOf(::SignWithGoogleViewModel)

}