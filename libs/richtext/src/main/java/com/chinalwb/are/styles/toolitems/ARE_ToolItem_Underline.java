package com.chinalwb.are.styles.toolitems;

import android.content.Context;
import android.text.Editable;
import android.text.style.CharacterStyle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chinalwb.are.AREditText;
import com.chinalwb.are.Constants;
import com.chinalwb.are.Util;
import com.chinalwb.are.spans.AreUnderlineSpan;
import com.chinalwb.are.styles.IARE_Style;
import com.chinalwb.are.styles.toolitems.styles.ARE_Style_Underline;

import Catatin.R;

/**
 * Created by wliu on 13/08/2018.
 */

public class ARE_ToolItem_Underline extends ARE_ToolItem_Abstract {

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
            IARE_ToolItem_Updater toolItemUpdater = getToolItemUpdater();
            mStyle = new ARE_Style_Underline(editText, (ImageView) toolItemView, toolItemUpdater);
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
            imageView.setImageResource(R.drawable.underline);
            imageView.bringToFront();
            toolItemView = imageView;
        }

        return toolItemView;
    }

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        boolean underlinedExists = false;

        AREditText editText = this.getEditText();
        Editable editable = editText.getEditableText();
        if (selStart > 0 && selStart == selEnd) {
            CharacterStyle[] styleSpans = editable.getSpans(selStart - 1, selStart, CharacterStyle.class);

            for (int i = 0; i < styleSpans.length; i++) {
                if (styleSpans[i] instanceof AreUnderlineSpan) {
                    underlinedExists = true;
                }
            }
        } else {
            //
            // Selection is a range
            CharacterStyle[] styleSpans = editable.getSpans(selStart, selEnd, CharacterStyle.class);

            for (int i = 0; i < styleSpans.length; i++) {

                if (styleSpans[i] instanceof AreUnderlineSpan) {
                    if (editable.getSpanStart(styleSpans[i]) <= selStart
                            && editable.getSpanEnd(styleSpans[i]) >= selEnd) {
                        underlinedExists = true;
                    }
                }
            }
        }

        toolItemUpdater.onCheckStatusUpdate(underlinedExists);
    }
}
