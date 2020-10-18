package `in`.catat.presentation.onboarding

import `in`.catat.data.dto.OnBoardingDataDto
import `in`.catat.domain.onboarding.repository.OnBoardingRepository
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * Created by pertadima on 17,October,2020
 */

class OnBoardingViewModel @ViewModelInject constructor(
    private val onBoardingRepository: OnBoardingRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val onBoardingLiveData = MutableLiveData<List<OnBoardingDataDto>>()
    fun observeOnBoardingData(): MutableLiveData<List<OnBoardingDataDto>> = onBoardingLiveData

    init {
        onBoardingLiveData.postValue(onBoardingRepository.getOnBoardingDataList())
    }
}