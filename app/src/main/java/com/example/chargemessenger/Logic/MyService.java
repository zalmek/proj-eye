package com.example.chargemessenger.Logic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.IBinder;

import com.example.chargemessenger.MainActivity;
import com.example.chargemessenger.R;

import java.io.File;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyService extends Service {
    private static final int NOTIFY_ID = 7;
    private static final String TAG = "bootLog";
    // Идентификатор канала
    private static final String CHANNEL_ID = "com.example.chargemessenger";
    public MyService() {
    }

    @Inject
    MyBroadcastReceiver myBroadcastReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        myBroadcastReceiver.context.registerReceiver(myBroadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        Intent notificationIntent = new Intent(this, MainActivity.class);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT);
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification =
                new Notification.Builder(this, CHANNEL_ID)
                        .setContentTitle("Dont worry")
                        .setContentText("Click to open the app. Works background by default")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentIntent(pendingIntent)
                        .setPriority(Notification.PRIORITY_LOW)
                        .setCategory(Notification.CATEGORY_SERVICE)
                        .setOngoing(true)
                        .build();

// Notification ID cannot be 0.
        startForeground(NOTIFY_ID, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}