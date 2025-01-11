package cd.zgeniuscoders.confidences.user.data.services

import cd.zgeniuscoders.confidences.user.domain.models.User
import cd.zgeniuscoders.confidences.user.domain.services.OnBoardingService
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseOnboadingServiceImpl(
    private val firestore: FirebaseFirestore
) : OnBoardingService {
    override fun createUser(user: User) {
        firestore.collection("users").document(user.userId).set(user)
    }
}