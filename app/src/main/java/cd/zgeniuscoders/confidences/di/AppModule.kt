package cd.zgeniuscoders.confidences.di

import cd.zgeniuscoders.confidences.chat.data.repository.FirebaseLatestMessageRepository
import cd.zgeniuscoders.confidences.chat.data.repository.FirebaseMessageRepository
import cd.zgeniuscoders.confidences.chat.data.services.ContactService
import cd.zgeniuscoders.confidences.chat.domain.repository.LatestMessageRepository
import cd.zgeniuscoders.confidences.chat.domain.repository.MessageRepository
import cd.zgeniuscoders.confidences.chat.presentation.chat.ChatViewModel
import cd.zgeniuscoders.confidences.chat.presentation.chat_lists.ChatListViewModel
import cd.zgeniuscoders.confidences.chat.presentation.contact_list.ContactListViewModel
import cd.zgeniuscoders.confidences.core.data.services.DatastoreSessionServiceImpl
import cd.zgeniuscoders.confidences.core.domain.services.SessionService
import cd.zgeniuscoders.confidences.core.presentation.AppViewModel
import cd.zgeniuscoders.confidences.user.data.repository.FirebaseUserRepository
import cd.zgeniuscoders.confidences.user.domain.repository.UserRepository
import cd.zgeniuscoders.confidences.user.presentation.onboarding.OnboardingViewModel
import cd.zgeniuscoders.confidences.user.presentation.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    single<FirebaseFirestore> {
        FirebaseFirestore.getInstance()
    }

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    factory<String?> {
        FirebaseAuth.getInstance().currentUser?.uid
    }

    single<ContactService> {
        ContactService(androidContext())
    }

    factory<SessionService> {
        DatastoreSessionServiceImpl(androidContext())
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

    viewModelOf(::ProfileViewModel)
    viewModelOf(::AppViewModel)
    viewModelOf(::ChatViewModel)
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::ChatListViewModel)
    viewModelOf(::ContactListViewModel)

}