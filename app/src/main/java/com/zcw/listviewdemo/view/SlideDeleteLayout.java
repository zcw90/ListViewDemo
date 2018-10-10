package com.zcw.listviewdemo.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.zcw.listviewdemo.util.DisplayUtil;

/**
 * Created by 朱城委 on 2018/9/30.<br><br>
 */
public class SlideDeleteLayout extends ConstraintLayout {
    private static final String TAG = SlideDeleteLayout.class.getSimpleName();

    private int lastX;
    private int lastY;

    /** 被认为是滑动的最短距离（单位：px） */
    private int touchSlop;

    /** 弹性滑动对象 */
    private Scroller scroller;

    public SlideDeleteLayout(Context context) {
        super(context);
        init(context);
    }

    public SlideDeleteLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideDeleteLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        scroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                return true;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                int deltaY = y - lastY;
                int deltaScroll = DisplayUtil.dip2px(getContext(), deltaX > 0 ? -60 : 0);
                if(Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > touchSlop) {
                    smoothScrollTo(deltaScroll, getScrollY(), 300);

                    lastX = x;
                    lastY = y;
                    return true;
                }

            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 指定时间内弹性滑动到目的地。
     * @param destX 目的地x坐标
     * @param destY 目的地y坐标
     * @param duration 滑动持续的时间
     */
    private void smoothScrollTo(int destX, int destY, int duration) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int deltaX = destX - scrollX;
        int deltaY = destY - scrollY;
        scroller.startScroll(scrollX, scrollY, deltaX, deltaY, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }
}
