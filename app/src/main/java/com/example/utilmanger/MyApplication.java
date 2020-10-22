package com.example.utilmanger;

import android.app.Application;

import com.example.utilmanger.manger.FontsManger;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 全局更改字体：第一步
         */
        FontsManger.setDefaultFont(this, "SERIF", "fonts/fz_biao_ys.ttf");
    }
}
