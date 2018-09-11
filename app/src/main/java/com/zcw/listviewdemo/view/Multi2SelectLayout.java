package com.zcw.listviewdemo.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.Checkable;

import com.zcw.base.LogUtil;

/**
 * Created by 朱城委 on 2018/8/31.<br><br>
 * 用于多选列表的布局
 */
public class Multi2SelectLayout extends ConstraintLayout implements Checkable {
    private static final String TAG = Multi2SelectLayout.class.getSimpleName();

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    /** 是否被选中 */
    private boolean isChecked;

    public Multi2SelectLayout(Context context) {
        super(context);
    }

    public Multi2SelectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Multi2SelectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
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
        setChecked(!isChecked);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

        if (isChecked())
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);

        return drawableState;
    }
}
