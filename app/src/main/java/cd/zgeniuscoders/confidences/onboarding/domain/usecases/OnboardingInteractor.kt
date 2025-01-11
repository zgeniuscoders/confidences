package cd.zgeniuscoders.confidences.onboarding.domain.usecases

import cd.zgeniuscoders.confidences.onboarding.domain.services.OnBoardingService

class OnboardingInteractor(
    val createUser: CreateUser
) {

    companion object {
        fun build(service: OnBoardingService): OnboardingInteractor {
            return OnboardingInteractor(CreateUser(service))
        }
    }

}