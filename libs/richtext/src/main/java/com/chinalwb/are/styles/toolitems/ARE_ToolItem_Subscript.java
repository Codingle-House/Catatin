package com.chinalwb.are.styles.toolitems;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chinalwb.are.AREditText;
import com.chinalwb.are.Constants;
import Catatin.R;
import com.chinalwb.are.Util;
import com.chinalwb.are.spans.AreSubscriptSpan;
import com.chinalwb.are.styles.IARE_Style;
import com.chinalwb.are.styles.toolitems.styles.ARE_Style_Subscript;

/**
 * Created by wliu on 13/08/2018.
 */

public class ARE_ToolItem_Subscript extends ARE_ToolItem_Abstract {

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
            mStyle = new ARE_Style_Subscript(editText, (ImageView) toolItemView, toolItemUpdater);
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
            int size = Util.getPixelByDp(context, 30);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.subscript);
            imageView.bringToFront();
            toolItemView = imageView;
        }

        return toolItemView;
    }

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {

		boolean subscriptExists = false;

		//
		// Two cases:
		// 1. Selection is just a pure cursor
		// 2. Selection is a range
		Editable editable = this.getEditText().getEditableText();
		if (selStart > 0 && selStart == selEnd) {
			AreSubscriptSpan[] subscriptSpans = editable.getSpans(selStart - 1, selStart, AreSubscriptSpan.class);
			if (subscriptSpans != null && subscriptSpans.length > 0) {
                subscriptExists = true;
			}
		} else {
            AreSubscriptSpan[] subscriptSpans = editable.getSpans(selStart, selEnd, AreSubscriptSpan.class);
            if (subscriptSpans != null && subscriptSpans.length > 0) {
                if (editable.getSpanStart(subscriptSpans[0]) <= selStart
                        && editable.getSpanEnd(subscriptSpans[0]) >= selEnd) {
                    subscriptExists = true;
                }
            }
        }

        toolItemUpdater.onCheckStatusUpdate(subscriptExists);
    }
}
