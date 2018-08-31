package com.zcw.listviewdemo.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.Checkable;

import com.zcw.base.LogUtil;

/**
 * Created by 朱城委 on 2018/8/31.<br><br>
 * 用于单选列表的布局
 */
public class SingleSelectLayout extends ConstraintLayout implements Checkable {
    private static final String TAG = SingleSelectLayout.class.getSimpleName();

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    /** 是否被选中 */
    private boolean isChecked;

    public SingleSelectLayout(Context context) {
        super(context);
    }

    public SingleSelectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleSelectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        LogUtil.e(TAG, "setChecked: " + checked);

        if(isChecked != checked) {
            isChecked = checked;
            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        LogUtil.e(TAG, "toggle");
        setChecked(!isChecked);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

        if(isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
}
