package com.sjw.guoguo;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

/**
 * Created by sjw on 2018/3/19.
 */

public class GGApp extends Application{
    private String APPID="c71e5fb9ab2e9f0a5db6d340ca4cd36b";
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(getApplicationContext(),APPID);
    }
}
