package com.example.chargemessenger;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = getApplicationContext().registerReceiver(new MyBroadcastReceiver(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
}