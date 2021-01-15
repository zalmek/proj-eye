package com.example.chargemessenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class BootNotifyReceiver extends BroadcastReceiver {
    // Идентификатор уведомления
    private static final int NOTIFY_ID = 228;
    private static final String TAG = "bootLog";
    // Идентификатор канала
    private static String CHANNEL_ID = "Chargemes channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"boot complete");
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Напоминание")
                        .setContentText("Включи меня, пожалуйста")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }
}