package com.anxpp.calculator;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by u on 2016/3/3.
 * @author anxpp.com
 */
public class MyApplication extends Application {

    public MyApplication() {
    }

    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
