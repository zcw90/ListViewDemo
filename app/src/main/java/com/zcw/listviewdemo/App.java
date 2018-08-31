package com.zcw.listviewdemo;

import android.app.Application;

import com.zcw.base.LogUtil;

/**
 * Created by 朱城委 on 2018/8/24.<br><br>
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化Log工具
        LogUtil.syncIsDebug(this);
        LogUtil.setIsSaveLog(false);
    }
}
