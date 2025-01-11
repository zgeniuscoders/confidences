package cd.zgeniuscoders.confidences.user.domain.usecases

import cd.zgeniuscoders.confidences.user.domain.services.OnBoardingService

class OnboardingInteractor(
    val createUser: CreateUser
) {

    companion object {
        fun build(service: OnBoardingService): OnboardingInteractor {
            return OnboardingInteractor(CreateUser(service))
        }
    }

}