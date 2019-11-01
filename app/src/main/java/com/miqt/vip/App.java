package com.miqt.vip;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.analysys.track.AnalysysTracker;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.miqt.vip.bean.HotFix;
import com.miqt.vip.service.VipService;
import com.miqt.wand.Wand;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;


public class App extends Application implements Wand.MotorListener {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        Wand.get().init(this).listener(this);
        QbSdk.preInit(this);
        QbSdk.initX5Environment(this, null);
        try {
            Bmob.initialize(this, "9de701ecbdf29f956d2d0a951cf9d66d");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        CrashReport.initCrashReport(getApplicationContext(), "6d57bcf68c", false);
        hotfix();

        Intent intent = new Intent(this, VipService.class);
        startService(intent);

        AnalysysTracker.init(this, "7752552892442721d", "xiaotuvip");
        AnalysysTracker.setDebugMode(this, true);
    }

    private void hotfix() {
        try {
            BmobQuery<HotFix> query = new BmobQuery<HotFix>();
            query.setLimit(1)
                    .addWhereEqualTo("debug", BuildConfig.DEBUG)
                    .addWhereEqualTo("channel", AppUtils.getAppVersionCode())
                    .order("-versionCode")
                    .findObjects(new FindListener<HotFix>() {

                        @Override
                        public void done(List<HotFix> list, BmobException e) {
                            if (list.size() > 0 && e == null) {
                                HotFix fix = list.get(0);
                                int cv = SPUtils.getInstance().getInt("wand_last_file", -1);
                                Log.d("wandfix", "current dex version:" + cv);
                                if (fix.getVersionCode() > cv) {
                                    downloadAndTouch(fix);
                                }
                            } else {
                                Log.d("wandfix", "\"更新失败：${e?.message}\"");
                            }
                        }
                    });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void downloadAndTouch(final HotFix fix) {
        fix.getDexFile().download(new DownloadFileListener() {

            @Override
            public void onProgress(Integer integer, long l) {
                Log.d("wandfix_onProgress", integer.toString() + "%");
            }

            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Wand.get().attachPack(new File(s));
                    Log.d("wandfix", s);
                    SPUtils.getInstance().put("wand_last_file", fix.getVersionCode());
                } else {
                    Log.e("wandfix", e.toString());
                }
            }
        });
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
