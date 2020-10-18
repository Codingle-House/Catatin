package `in`.catat.data.dto

import `in`.catat.R
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

/**
 * Created by pertadima on 17,October,2020
 */

@Parcelize
data class OnBoardingDataDto(
    @DrawableRes val image: Int = R.drawable.onboarding_ic_content_1,
    @StringRes val title: Int = 0,
    @StringRes val description: Int = 0,
) : Parcelable