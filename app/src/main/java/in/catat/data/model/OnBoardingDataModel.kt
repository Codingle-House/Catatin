package `in`.catat.data.model

import `in`.catat.R
import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

/**
 * Created by pertadima on 22,August,2020
 */

@Parcelize
data class OnBoardingDataModel(
    @DrawableRes val image: Int = R.drawable.onboarding_ic_content_1,
    val title: String = "",
    val description: String = ""
) : Parcelable