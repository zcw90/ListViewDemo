package com.zcw.listviewdemo.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zcw.listviewdemo.R;
import com.zcw.listviewdemo.util.DisplayUtil;

/**
 * Created by 朱城委 on 2018/11/12.<br><br>
 * 侧滑布局
 */
public class SlideLayout extends ConstraintLayout {

    /** 显示内容的View */
    private View contentView;

    private SlideMenuItem slideMenuItem;

    private TextView slideMenuView;

    public SlideLayout(Context context) {
        super(context);
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public View getContentView() {
        return contentView;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    public SlideMenuItem getSlideMenuItem() {
        return slideMenuItem;
    }

    public void setSlideMenuItem(SlideMenuItem slideMenuItem) {
        this.slideMenuItem = slideMenuItem;

        slideMenuView = new TextView(getContext());
        slideMenuView.setText(slideMenuItem.getContent());
        slideMenuView.setTextColor(getContext().getResources().getColor(R.color.white));
        slideMenuView.setBackgroundResource(slideMenuItem.getBgColorResId());
        slideMenuView.setGravity(Gravity.CENTER);

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                DisplayUtil.dip2px(getContext(), 60), 0);
        params.leftToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        slideMenuView.setLayoutParams(params);
        addView(slideMenuView);
    }

    public TextView getSlideMenuView() {
        return slideMenuView;
    }

    public void setSlideMenuView(TextView slideMenuView) {
        this.slideMenuView = slideMenuView;
    }
}
