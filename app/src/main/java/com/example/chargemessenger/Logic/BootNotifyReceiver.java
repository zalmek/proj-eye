package com.example.chargemessenger.Logic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.chargemessenger.Logic.MyService;
import com.example.chargemessenger.MainActivity;
import com.example.chargemessenger.R;

public class BootNotifyReceiver extends BroadcastReceiver {
    // Идентификатор уведомления
    private static final int NOTIFY_ID = 228;
    private static final String TAG = "bootLog";
    // Идентификатор канала
    private static String CHANNEL_ID = "Chargemes channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "boot complete");
        Intent notificationIntent = new Intent(context, MainActivity.class);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT);
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);

        Notification notification =
                new Notification.Builder(context, CHANNEL_ID)
                        .setContentTitle("Dont worry")
                        .setContentText("Click to open the app. Works background by default")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentIntent(pendingIntent)
                        .setPriority(Notification.PRIORITY_LOW)
                        .setCategory(Notification.CATEGORY_SERVICE)
                        .setOngoing(true)
                        .build();

// Notification ID cannot be 0.
        context.startService(new Intent(context, MyService.class));
    }
}