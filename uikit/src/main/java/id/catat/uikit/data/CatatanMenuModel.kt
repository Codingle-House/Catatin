package id.catat.uikit.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by pertadima on 23,August,2020
 */

@Parcelize
class CatatanMenuModel(
    val title: String,
    val description: String = "",
    val isPremiumContent: Boolean = false
) : Parcelable