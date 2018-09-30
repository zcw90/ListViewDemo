package com.zcw.listviewdemo.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.zcw.base.CommonUtils;

/**
 * Created by 朱城委 on 2018/9/30.<br><br>
 */
public class SlideDeleteLayout extends ConstraintLayout {
    private static final String TAG = SlideDeleteLayout.class.getSimpleName();

    public SlideDeleteLayout(Context context) {
        super(context);
    }

    public SlideDeleteLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideDeleteLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return false;

            case MotionEvent.ACTION_MOVE:
                CommonUtils.toast(getContext(), "SlideDeleteLayout");
                return true;

            case MotionEvent.ACTION_UP:
                return true;
        }
        return super.onTouchEvent(event);
    }


}
