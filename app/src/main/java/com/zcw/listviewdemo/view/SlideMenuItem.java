package com.zcw.listviewdemo.view;

/**
 * Created by 朱城委 on 2018/11/9.<br><br>
 * 滑动菜单属性类
 */
public class SlideMenuItem {
    /** 滑动菜单内容 */
    private String content;

    /** 滑动菜单背景颜色 */
    private int bgColorResId;

    private int menuWidth;

    public SlideMenuItem() {
    }

    public SlideMenuItem(String content, int bgColorResId) {
        this.content = content;
        this.bgColorResId = bgColorResId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBgColorResId() {
        return bgColorResId;
    }

    public void setBgColorResId(int bgColorResId) {
        this.bgColorResId = bgColorResId;
    }

    public int getMenuWidth() {
        return menuWidth;
    }

    public void setMenuWidth(int menuWidth) {
        this.menuWidth = menuWidth;
    }
}
