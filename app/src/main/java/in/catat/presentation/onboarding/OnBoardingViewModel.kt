package `in`.catat.presentation.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.catat.data.dto.OnBoardingDataDto
import `in`.catat.domain.onboarding.repository.OnBoardingRepository
import javax.inject.Inject

/**
 * Created by pertadima on 17,October,2020
 */

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository
) : ViewModel() {

    private val onBoardingLiveData = MutableLiveData<List<OnBoardingDataDto>>()
    fun observeOnBoardingData(): MutableLiveData<List<OnBoardingDataDto>> = onBoardingLiveData

    init {
        onBoardingLiveData.postValue(onBoardingRepository.getOnBoardingDataList())
    }
}