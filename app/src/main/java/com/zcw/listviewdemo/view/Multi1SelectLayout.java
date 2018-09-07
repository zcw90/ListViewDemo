package com.zcw.listviewdemo.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcw.base.LogUtil;
import com.zcw.listviewdemo.R;

/**
 * Created by 朱城委 on 2018/8/31.<br><br>
 * 用于单选列表的布局
 */
public class Multi1SelectLayout extends ConstraintLayout implements Checkable {
    private static final String TAG = Multi1SelectLayout.class.getSimpleName();

    /** 是否被选中 */
    private boolean isChecked;

    public Multi1SelectLayout(Context context) {
        super(context);
    }

    public Multi1SelectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Multi1SelectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
    public void refreshDrawableState() {
        super.refreshDrawableState();
        for(int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if(view.getId() == R.id.tv_multi_item_content1) {
                if(isChecked) {
                    ((TextView)view).setTextColor(ContextCompat.getColor(getContext(), R.color.text_select));
                }
                else {
                    ((TextView)view).setTextColor(ContextCompat.getColor(getContext(), R.color.text_normal));
                }
            }
            else if(view.getId() == R.id.img_multi_item_content1) {
                if(isChecked) {
                    ((ImageView)view).setImageResource(R.mipmap.select_multi);
                }
                else {
                    ((ImageView)view).setImageResource(R.mipmap.select_multi_no);
                }
            }
        }
    }
}
