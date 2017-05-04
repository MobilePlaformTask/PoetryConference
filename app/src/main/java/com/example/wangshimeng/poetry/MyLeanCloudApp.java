package com.example.wangshimeng.poetry;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by wangshimeng on 2017/4/23.
 */

public class MyLeanCloudApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"HLaGEPrBYY5190VFNAEVl4sL-gzGzoHsz","Ydc2iDG1f68xLdjmyxbJMos0");
        AVOSCloud.setDebugLogEnabled(true);
    }
}

