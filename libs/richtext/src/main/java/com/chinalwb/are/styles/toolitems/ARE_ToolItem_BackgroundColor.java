package com.chinalwb.are.styles.toolitems;

import android.content.Context;
import android.text.Editable;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chinalwb.are.AREditText;
import com.chinalwb.are.Constants;
import com.chinalwb.are.Util;
import com.chinalwb.are.styles.IARE_Style;
import com.chinalwb.are.styles.toolitems.styles.ARE_Style_BackgroundColor;

import Catatin.R;

public class ARE_ToolItem_BackgroundColor extends ARE_ToolItem_Abstract {

    @Override
    public IARE_ToolItem_Updater getToolItemUpdater() {
        if (toolItemUpdater == null) {
            toolItemUpdater = new ARE_ToolItem_UpdaterDefault(this, Constants.CHECKED_COLOR, Constants.UNCHECKED_COLOR);
            setToolItemUpdater(toolItemUpdater);
        }
        return toolItemUpdater;
    }


    @Override
    public IARE_Style getStyle() {
        if (mStyle == null) {
            AREditText editText = this.getEditText();
            mStyle = new ARE_Style_BackgroundColor(editText, (ImageView) toolItemView, getToolItemUpdater(), Constants.COLOR_BACKGROUND_DEFAULT);
        }
        return mStyle;
    }

    @Override
    public View getView(Context context) {
        if (null == context) {
            return toolItemView;
        }
        if (toolItemView == null) {
            ImageView imageView = new ImageView(context);
            int size = Util.getPixelByDp(context, 40);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.background);
            imageView.bringToFront();
            toolItemView = imageView;
        }

        return toolItemView;
    }

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        boolean backgroundColorExists = false;

        AREditText editText = this.getEditText();
        Editable editable = editText.getEditableText();
        if (selStart > 0 && selStart == selEnd) {
            BackgroundColorSpan[] backgroundColorSpans = editable.getSpans(selStart - 1, selStart, BackgroundColorSpan.class);
            if (backgroundColorSpans != null && backgroundColorSpans.length > 0) {
                backgroundColorExists = true;
            }
        } else {
            BackgroundColorSpan[] backgroundColorSpans = editable.getSpans(selStart, selEnd, BackgroundColorSpan.class);
            if (backgroundColorSpans != null && backgroundColorSpans.length > 0) {
                if (editable.getSpanStart(backgroundColorSpans[0]) <= selStart
                        && editable.getSpanEnd(backgroundColorSpans[0]) >= selEnd) {
                    backgroundColorExists = true;
                }
            }
        }

        toolItemUpdater.onCheckStatusUpdate(backgroundColorExists);
    }

}
