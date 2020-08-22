package `in`.catat.presentation.onboarding

import `in`.catat.R
import `in`.catat.data.model.OnBoardingDataModel
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.api.load
import kotlinx.android.synthetic.main.fragment_onboarding.*

/**
 * Created by pertadima on 22,August,2020
 */

class OnBoardingFragment : Fragment(R.layout.fragment_onboarding) {

    private val onBoardingDataModel by lazy {
        arguments?.getParcelable(ON_BOARDING_DATA_KEY) ?: OnBoardingDataModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        onboarding_imageview_icon.load(onBoardingDataModel.image)
        onboarding_textview_title.text = onBoardingDataModel.title
        onboarding_textview_description.text = onBoardingDataModel.description
    }

    companion object {
        private const val ON_BOARDING_DATA_KEY = "ON_BOARDING_DATA_KEY"

        fun newInstance(onBoardingDataModel: OnBoardingDataModel): OnBoardingFragment {
            return OnBoardingFragment().apply {
                arguments = bundleOf(ON_BOARDING_DATA_KEY to onBoardingDataModel)
            }
        }
    }
}