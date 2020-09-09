package com.chinalwb.are.styles.toolitems;

import android.view.View;

import com.chinalwb.are.styles.IARE_Style;

/**
 * The default tool item check status updater.
 */

public class ARE_ToolItem_UpdaterDefault implements IARE_ToolItem_Updater {

    private IARE_ToolItem toolItem;

    private int mCheckedColor;

    private int mUncheckedColor;

    public ARE_ToolItem_UpdaterDefault(IARE_ToolItem toolItem, int checkedColor, int uncheckedColor) {
        this.toolItem = toolItem;
        mCheckedColor = checkedColor;
        mUncheckedColor = uncheckedColor;
    }

    @Override
    public void onCheckStatusUpdate(boolean checked) {
        IARE_Style areStyle = toolItem.getStyle();
        areStyle.setChecked(checked);
        View view = toolItem.getView(null);
        int color = checked ? mCheckedColor : mUncheckedColor;
        view.setBackgroundColor(color);
    }
}
