package com.jd.lib;

import android.app.Application;
import android.content.Context;

import com.jd.lib.util.ScreenUtils;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class App extends Application {
    private static App instance;
    public static int sScreenHeight, sScreenWidth;

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        sScreenHeight = ScreenUtils.getScreenHeight(this);
        sScreenWidth = ScreenUtils.getScreenWidth(this);
    }

    public void setInstance(App application) {
        instance = application;
    }

    public static App getContext() {
        return instance;
    }

}