package com.miqt.vip;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.analysys.track.AnalysysTracker;
import com.blankj.utilcode.util.Utils;
import com.miqt.vip.service.VipService;
import com.miqt.wand.Wand;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;


public class App extends Application implements Wand.MotorListener {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        Wand.get().init(this).listener(this);
        QbSdk.preInit(this);
        QbSdk.initX5Environment(this, null);
        Bugly.init(getApplicationContext(), "6d57bcf68c", false);

        hotfix();

        Intent intent = new Intent(this, VipService.class);
        startService(intent);

        AnalysysTracker.init(this, "7752552892442721d", "xiaotuvip");
        AnalysysTracker.setDebugMode(this, true);
    }

    private void hotfix() {

    }


    @Override
    public void initFinish() {
        Log.d("wandfix", "initFinish");
    }

    @Override
    public void initError(Throwable throwable) {
        Log.e("wandfix", throwable.toString());
    }

    @Override
    public void onNewPackAttach(File file) {
        Log.d("wandfix", "new fix pack:" + file.getAbsolutePath());
    }
}
