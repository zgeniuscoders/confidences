package cd.zgeniuscoders.confidences.di

import cd.zgeniuscoders.confidences.chat.data.repository.FirebaseLatestMessageRepository
import cd.zgeniuscoders.confidences.chat.data.repository.FirebaseMessageRepository
import cd.zgeniuscoders.confidences.chat.domain.repository.LatestMessageRepository
import cd.zgeniuscoders.confidences.chat.domain.repository.MessageRepository
import cd.zgeniuscoders.confidences.chat.presentation.chat_lists.ChatListViewModel
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

    single<String?> {
        FirebaseAuth.getInstance().currentUser?.uid
    }

    single<UserRepository> {
        FirebaseUserRepository(
            get()
        )
    }

    single<MessageRepository> {
        FirebaseMessageRepository(get())
    }

    single<LatestMessageRepository> {
        FirebaseLatestMessageRepository(get())
    }

    viewModelOf(::OnboardingViewModel)
    viewModelOf(::ChatListViewModel)

}