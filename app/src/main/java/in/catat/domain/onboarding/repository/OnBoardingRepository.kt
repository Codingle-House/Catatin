package `in`.catat.domain.onboarding.repository

import `in`.catat.data.dto.OnboardingDataDto
import `in`.catat.domain.onboarding.datasource.OnBoardingLocalDataSource
import javax.inject.Inject

/**
 * Created by pertadima on 17,October,2020
 */

class OnBoardingRepository @Inject constructor(
    private val onBoardingLocalDataSource: OnBoardingLocalDataSource
) {
    fun getOnBoardingDataList(): List<OnboardingDataDto> {
        return onBoardingLocalDataSource.onBoardingDataList
    }
}