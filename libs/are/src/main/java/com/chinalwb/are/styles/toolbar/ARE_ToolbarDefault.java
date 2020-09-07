package com.chinalwb.are.styles.toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.chinalwb.are.AREditText;
import com.chinalwb.are.styles.toolitems.IARE_ToolItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wliu on 13/08/2018.
 */
public class ARE_ToolbarDefault extends HorizontalScrollView implements IARE_Toolbar {

    private Context context;

    private LinearLayout container;

    private List<IARE_ToolItem> mToolItems = new ArrayList<>();

    private AREditText mAREditText;

    public ARE_ToolbarDefault(Context context) {
        this(context, null);
    }

    public ARE_ToolbarDefault(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ARE_ToolbarDefault(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = (Activity) context;
        initSelf();
    }

    @Override
    public void addToolbarItem(IARE_ToolItem toolbarItem) {
        toolbarItem.setToolbar(this);
        mToolItems.add(toolbarItem);
        View view = toolbarItem.getView(context);
        if (view != null) {
            container.addView(view);
        }
    }

    @Override
    public List<IARE_ToolItem> getToolItems() {
        return mToolItems;
    }

    @Override
    public void setEditText(AREditText editText) {
        this.mAREditText = editText;
    }

    @Override
    public AREditText getEditText() {
        return mAREditText;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (IARE_ToolItem toolItem : this.mToolItems) {
            toolItem.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initSelf() {
        container = new LinearLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        container.setGravity(Gravity.CENTER_VERTICAL);
        container.setLayoutParams(params);
        this.addView(container);
    }
}
