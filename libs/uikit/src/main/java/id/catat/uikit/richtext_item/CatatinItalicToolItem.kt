package id.catat.uikit.richtext_item

import Catatin.R
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.chinalwb.are.Util
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Italic
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_UpdaterDefault
import com.chinalwb.are.styles.toolitems.IARE_ToolItem_Updater
import id.co.catatin.core.ext.getDrawableCompat

/**
 * Created by pertadima on 27,August,2020
 */

class CatatinItalicToolItem(
    private val checkedColor: Int,
    private val unCheckedColor: Int,
    @DrawableRes private val icon: Int = R.drawable.richtext_ic_italic
) : ARE_ToolItem_Italic() {
    override fun getToolItemUpdater(): IARE_ToolItem_Updater {
        if (toolItemUpdater == null) {
            toolItemUpdater = ARE_ToolItem_UpdaterDefault(
                this,
                checkedColor,
                unCheckedColor
            )
        }
        return toolItemUpdater
    }

    override fun getView(context: Context?): View? {
        if (null == context) {
            return toolItemView
        }
        if (toolItemView == null) {
            val imageView = ImageView(context)
            val size = Util.getPixelByDp(context, 40)
            val params = LinearLayout.LayoutParams(size, size)
            imageView.apply {
                layoutParams = params
                setImageDrawable(context.getDrawableCompat(icon))
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                bringToFront()
            }
            toolItemView = imageView
        }
        return toolItemView
    }
}