package com.example.chargemessenger;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
@AndroidEntryPoint
public class MyService extends Service {
    public MyService(){}
    @Inject MyBroadcastReceiver myBroadcastReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getApplicationContext().registerReceiver(myBroadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }
}