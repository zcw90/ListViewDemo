package com.zcw.listviewdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

import com.zcw.base.LogUtil;
import com.zcw.listviewdemo.util.DisplayUtil;

/**
 * Created by 朱城委 on 2018/9/21.<br><br>
 * 左右滑动删除ListView
 */
public class SlideDeleteListView extends ListView {

    private int downX;
    private int downY;
    private int lastX;

    /** 被认为是滑动的最短距离（单位：px） */
    private int touchSlop;
    private Scroller scroller;
    private VelocityTracker velocityTracker;

    /** 是否左右滑动 */
    private boolean isSlide;

    /** 左右滑动的view */
    private View slideViewItem;

    /** 滑动打开的view */
    private View slideViewItemOpen;

    /** 左右滑动的位置 */
    private int slidePosition;

    /** 滑动打开View的位置 */
    private int slidePositionOpen;

    /** 滑动菜单的宽度 */
    private int slideMenuWidth;

    public SlideDeleteListView(Context context) {
        super(context);
        init(context);
    }

    public SlideDeleteListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideDeleteListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        scroller = new Scroller(context);
        velocityTracker = VelocityTracker.obtain();
        slideMenuWidth = DisplayUtil.dip2px(getContext(), -60);
        slidePosition = AdapterView.INVALID_POSITION;
        slidePositionOpen = slidePosition;

        slideViewItemOpen = null;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int)ev.getY();
                lastX = downX;

                if(slideViewItemOpen != null) {
                    break;
                }

                // 获取有效的position
                slidePosition = pointToPosition(downX, downY);
                if(slidePosition == AdapterView.INVALID_POSITION) {
                    return super.dispatchTouchEvent(ev);
                }

                // 获取点击的view
                slideViewItem = getChildAt(slidePosition - getFirstVisiblePosition());
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) ev.getX() - downX;
                int deltaY = (int) ev.getY() - downY;
                if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > touchSlop) {
                    isSlide = true;
                }
                else {
                    isSlide = false;
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isSlide && slidePosition != AdapterView.INVALID_POSITION) {
            velocityTracker.addMovement(ev);
            int x = (int)ev.getX();

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;

                case MotionEvent.ACTION_MOVE:
                    // 取消此次事件序列中已经传递给父类的事件
                    cancelSuperHandleMotionEvent(ev);
                    int positionTemp = pointToPosition((int) ev.getX(), (int) ev.getY());
                    if(slideViewItemOpen != null && slidePosition != positionTemp) {
                        smoothCloseSlideMenu();
                        break;
                    }

                    int deltaX = lastX - x;
                    int scrollX = slideViewItem.getScrollX() + deltaX;
                    if(Math.abs(scrollX) <= Math.abs(slideMenuWidth) && scrollX <= 0) {
                        slideViewItem.scrollBy(deltaX, 0);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    isSlide = false;
                    velocityTracker.computeCurrentVelocity(300);
                    float velocityX = velocityTracker.getXVelocity();

                    if (velocityX > 50) {
                        smoothOpenSlideMenu();
                    }
                    else if(velocityX >= 0 && velocityX <=50) {
                        if(Math.abs(slideViewItem.getScrollX()) >= Math.abs(slideMenuWidth / 2)) {
                            smoothOpenSlideMenu();
                        }
                        else {
                            smoothCloseSlideMenu();
                        }
                    }
                    else if(velocityX < 0 && velocityX >= -50) {
                        if(Math.abs(slideViewItem.getScrollX()) <= Math.abs(slideMenuWidth  * 2 / 3)) {
                            smoothCloseSlideMenu();
                        }
                        else {
                            smoothOpenSlideMenu();
                        }
                    }
                    else if (velocityX < -50) {
                        smoothCloseSlideMenu();
                    }

                    velocityTracker.clear();
                    break;
            }

            lastX = x;
            return true;
        }
        else if(slidePositionOpen != AdapterView.INVALID_POSITION) {
            cancelSuperHandleMotionEvent(ev);

            switch (ev.getAction()) {
                case MotionEvent.ACTION_UP:
                    smoothCloseSlideMenu();
                    return true;
            }
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 取消父类处理事件
     * @param event
     */
    private void cancelSuperHandleMotionEvent(MotionEvent event) {
        MotionEvent cancelEvent = MotionEvent.obtain(event);
        cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                (event.getActionIndex()<< MotionEvent.ACTION_POINTER_INDEX_SHIFT));
        super.onTouchEvent(cancelEvent);
    }

    /**
     * 指定时间内弹性滑动到目的地。
     * @param destX 目的地x坐标
     * @param destY 目的地y坐标
     * @param duration 滑动持续的时间
     */
    private void smoothScrollTo(int destX, int destY, int duration) {
        int scrollX = slideViewItem.getScrollX();
        int scrollY = slideViewItem.getScrollY();
        int deltaX = destX - scrollX;
        int deltaY = destY - scrollY;
        scroller.startScroll(scrollX, scrollY, deltaX, deltaY, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset()) {
            slideViewItem.scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        velocityTracker.recycle();
        super.onDetachedFromWindow();
    }

    /**
     * 平滑打开滑动菜单
     */
    private void smoothOpenSlideMenu() {
        smoothScrollTo(slideMenuWidth, 0, 300);
        slidePositionOpen = slidePosition;
        slideViewItemOpen = slideViewItem;
    }

    /**
     * 平滑关闭滑动菜单
     */
    private void smoothCloseSlideMenu() {
        smoothScrollTo(0, 0, 300);
        slidePosition = AdapterView.INVALID_POSITION;
        slidePositionOpen = slidePosition;

        slideViewItemOpen = null;
    }
}
