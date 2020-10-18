package `in`.catat.domain.onboarding.datasource

import `in`.catat.R
import `in`.catat.data.dto.OnBoardingDataDto
import javax.inject.Inject

/**
 * Created by pertadima on 17,October,2020
 */

class OnBoardingLocalDataSource @Inject constructor() {
    val onBoardingDataList: List<OnBoardingDataDto>
        get() = listOf(
            OnBoardingDataDto(
                image = R.drawable.onboarding_ic_content_1,
                title = R.string.onboarding_title_content1,
                description = R.string.onboarding_text_content1
            ),
            OnBoardingDataDto(
                image = R.drawable.onboarding_ic_content_2,
                title = R.string.onboarding_title_content2,
                description = R.string.onboarding_text_content2
            ),
            OnBoardingDataDto(
                image = R.drawable.onboarding_ic_content_3,
                title = R.string.onboarding_title_content3,
                description = R.string.onboarding_text_content3
            )
        )
}