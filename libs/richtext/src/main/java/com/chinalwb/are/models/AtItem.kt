package com.chinalwb.are.models

import android.graphics.Color
import java.io.Serializable

/**
 * Created by wliu on 2018/2/4.
 */

class AtItem(
    @JvmField
    var mKey: String,
    @JvmField
    var mName: String,
    @JvmField
    var color: Int = Color.BLUE
) : Serializable {
    @JvmField
    var mIconId: Int = mKey.toInt()

    @JvmField
    var mColor: Int = color
}