package cd.zgeniuscoders.confidences.onboarding.domain.usecases

import cd.zgeniuscoders.confidences.authentication.domain.models.User
import cd.zgeniuscoders.confidences.onboarding.domain.services.OnBoardingService

class CreateUser(private val service: OnBoardingService) {

    fun run(user: User){
        service.createUser(user)
    }

}