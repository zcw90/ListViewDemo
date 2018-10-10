package com.zcw.listviewdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by 朱城委 on 2018/9/19.<br><br>
 * 滑动删除ListView
 */
public class SlideListView extends ListView {

    // 分别记录上次滑动的坐标
    private int lastXIntercept;
    private int lastYIntercept;

    public SlideListView(Context context) {
        super(context);
    }

    public SlideListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideListView(Context context, AttributeSet attrs, int defStyleAttr) {
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
                int deltaX = x - lastXIntercept;
                int deltaY = y - lastYIntercept;
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

        return intercepted;
    }
}
