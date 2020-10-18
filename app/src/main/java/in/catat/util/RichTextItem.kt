package `in`.catat.util

import `in`.catat.R
import android.content.Context
import com.chinalwb.are.styles.toolitems.IARE_ToolItem
import id.catat.uikit.richtext_item.*
import id.co.catatin.core.ext.getColorCompat

/**
 * Created by pertadima on 18,October,2020
 */

object RichTextItem {
    fun richTextDefaultItem(context: Context): List<IARE_ToolItem> {
        val checkedBackgroundColor = context.getColorCompat(R.color.colorSelectedToolItem)
        val uncheckedBackgroundColor = context.getColorCompat(android.R.color.transparent)

        val alignmentLeft = CatatinAlignmentLeftToolItem()
        val alignmentCenter = CatatinAlignmentCenterToolItem()
        val alignmentRight = CatatinAlignmentRightToolItem()

        val bold = CatatinBoldToolItem(checkedBackgroundColor, uncheckedBackgroundColor)
        val italic = CatatinItalicToolItem(checkedBackgroundColor, uncheckedBackgroundColor)
        val underline =
            CatatinUnderlineToolItem(checkedBackgroundColor, uncheckedBackgroundColor)
        val strikeTrough =
            CatatinStrikeThroughToolItem(checkedBackgroundColor, uncheckedBackgroundColor)

        val quote = CatatinQuoteToolItem(checkedBackgroundColor, uncheckedBackgroundColor)

        val numberList =
            CatatinNumberListToolItem(checkedBackgroundColor, uncheckedBackgroundColor)
        val bulletList =
            CatatinBulletListToolItem(checkedBackgroundColor, uncheckedBackgroundColor)
        val divider = CatatinDividerToolItem()

        return listOf(
            alignmentLeft,
            alignmentCenter,
            alignmentRight,
            bold,
            italic,
            underline,
            strikeTrough,
            quote,
            numberList,
            bulletList,
            divider
        )
    }
}