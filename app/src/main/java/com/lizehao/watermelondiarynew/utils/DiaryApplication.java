package com.lizehao.watermelondiarynew.utils;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by developerHaoz on 2017/5/9.
 */

public class DiaryApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
      }
        LeakCanary.install(this);
    }
}
