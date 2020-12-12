package com.example.chargemessenger;

import android.app.Service;
import android.content.*;
import android.os.*;
import android.widget.Toast;

public class MyService extends Service {

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
        MyBroadcastReceiver br =new MyBroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {//  не срабатывает
                Toast.makeText(context,"workS!!!",Toast.LENGTH_SHORT);
            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);// мб здесь
        registerReceiver(br,filter); //мб здесь
        Toast.makeText(context, "Service is still running", Toast.LENGTH_LONG).show();
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, 10000);
            }
        };
        handler.postDelayed(runnable, 15000);
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startid) {

    }
}
