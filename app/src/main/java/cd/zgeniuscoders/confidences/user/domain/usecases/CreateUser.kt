package cd.zgeniuscoders.confidences.user.domain.usecases

import cd.zgeniuscoders.confidences.user.domain.models.User
import cd.zgeniuscoders.confidences.user.domain.services.OnBoardingService

class CreateUser(private val service: OnBoardingService) {

    fun run(user: User){
        service.createUser(user)
    }

}