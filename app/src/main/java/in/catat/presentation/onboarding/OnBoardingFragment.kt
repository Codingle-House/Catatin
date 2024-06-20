package `in`.catat.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.load
import `in`.catat.R
import `in`.catat.data.dto.OnBoardingDataDto
import `in`.catat.databinding.FragmentOnboardingBinding

/**
 * Created by pertadima on 22,August,2020
 */

class OnBoardingFragment : Fragment(R.layout.fragment_onboarding) {

    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOnboardingBinding
        get() = FragmentOnboardingBinding::inflate

    private lateinit var binding: FragmentOnboardingBinding

    private val onBoardingDataModel by lazy {
        arguments?.getParcelable(ON_BOARDING_DATA_KEY) ?: OnBoardingDataDto()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = bindingInflater(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() = with(binding) {
        onboardingImageviewIcon.load(onBoardingDataModel.image)
        onboardingTextviewTitle.text = getString(onBoardingDataModel.title)
        onboardingTextviewDescription.text = getString(onBoardingDataModel.description)
    }

    companion object {
        private const val ON_BOARDING_DATA_KEY = "ON_BOARDING_DATA_KEY"

        fun newInstance(onBoardingDataModel: OnBoardingDataDto): OnBoardingFragment {
            return OnBoardingFragment().apply {
                arguments = bundleOf(ON_BOARDING_DATA_KEY to onBoardingDataModel)
            }
        }
    }
}