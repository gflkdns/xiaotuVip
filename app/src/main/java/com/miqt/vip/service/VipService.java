package com.miqt.vip.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class VipService extends Service {
    private String CHANNEL_ID = "sdk_channel_id";
    private String CHANNEL_NAME = "保活的_不占资源求放过";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_HIGH);
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.createNotificationChannel(channel);
                Notification.Builder builder = new Notification.Builder(getApplicationContext(), CHANNEL_ID);
                Notification notification = builder.setContentText("友好提示").setContentTitle("提示").build();
                startForeground(1, notification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }


}
