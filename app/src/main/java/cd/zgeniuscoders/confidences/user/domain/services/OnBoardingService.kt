package cd.zgeniuscoders.confidences.user.domain.services

import cd.zgeniuscoders.confidences.user.domain.models.User

interface OnBoardingService {

    fun createUser(user: User)

}