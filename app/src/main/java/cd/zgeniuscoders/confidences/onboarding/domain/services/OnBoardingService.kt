package cd.zgeniuscoders.confidences.onboarding.domain.services

import cd.zgeniuscoders.confidences.authentication.domain.models.User

interface OnBoardingService {

    fun createUser(user: User)

}