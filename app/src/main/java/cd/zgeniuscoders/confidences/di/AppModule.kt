package cd.zgeniuscoders.confidences.di

import cd.zgeniuscoders.confidences.user.data.repository.FirebaseUserRepository
import cd.zgeniuscoders.confidences.user.domain.repository.UserRepository
import cd.zgeniuscoders.confidences.user.presentation.OnboardingViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    single<FirebaseFirestore> {
        FirebaseFirestore.getInstance()
    }

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single<UserRepository> {
        FirebaseUserRepository(
            get()
        )
    }

    viewModelOf(::OnboardingViewModel)

}