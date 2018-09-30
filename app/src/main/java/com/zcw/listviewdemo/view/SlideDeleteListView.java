package com.zcw.listviewdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by 朱城委 on 2018/9/21.<br><br>
 * 左右滑动删除ListView
 */
public class SlideDeleteListView extends ListView {

    private int lastInterceptX;
    private int lastInterceptY;

    public SlideDeleteListView(Context context) {
        super(context);
    }

    public SlideDeleteListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideDeleteListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int)ev.getX();
        int y = (int)ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastInterceptX;
                int deltaY = y - lastInterceptY;
                if(Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercepted = false;
                }
                else {
                    intercepted = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;

            default:
                break;
        }

        lastInterceptX = x;
        lastInterceptY = y;
        return intercepted;
    }
}
