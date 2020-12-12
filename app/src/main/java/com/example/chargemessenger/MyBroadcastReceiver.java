package com.example.chargemessenger;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyBroadcastReceiver extends BroadcastReceiver {
    Boolean charger=false;
    @Override
    public void onReceive(Context context, Intent intent) {// +- работает
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s ";

        String apiToken = "1448041949:AAGKZXLqa7MTi25uE3JflofJrFadzY0KQSc";

        // really need a func
        String chatId = "904847378";
        String text = "Hello_world123!";

        urlString = String.format(urlString, apiToken, chatId, text);

        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            Toast.makeText(context, "The device is charging", Toast.LENGTH_SHORT).show();
            charger = true;

        }
        else {
            intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED);
            Toast.makeText(context, "The device is not charging", Toast.LENGTH_SHORT).show();
            charger = false;
        }
        String plug = isConnected(context);

        Toast.makeText(context, ""+plug, Toast.LENGTH_SHORT).show();


        checking(context,charger);
    }


    public static String isConnected(Context context) {
        String status = null;
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        if (plugged == BatteryManager.BATTERY_PLUGGED_AC) {
            status="power";

        } else if (plugged == BatteryManager.BATTERY_PLUGGED_USB) {
            status="USB";

        } else if (plugged == 0) {
            status="OnBattery";
        }

        return status;

    }

    public void checking(Context context,Boolean charger){

        Intent intent = context.registerReceiver(null,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        int curLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        Intent notify = new Intent(context, MainActivity.class);
        Notification noti;
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, notify, 0);

        if(curLevel <= 15 && !charger) {
            noti = new Notification.Builder(context).setTicker("Ticker Title")
                    .setContentTitle("Battery Low")
                    .setContentText("Battery " + curLevel + "% Please Connect Charger")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent).getNotification();
            noti.flags = Notification.FLAG_AUTO_CANCEL;
            NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            nm.notify(0, noti);
        }
        else if(curLevel == 100 && charger){
            noti = new Notification.Builder(context).setTicker("Ticker Title")
                    .setContentTitle("Battery Okay")
                    .setContentText("Battery full Please disconnect Charger")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent).getNotification();
            noti.flags = Notification.FLAG_AUTO_CANCEL;
            NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            nm.notify(0, noti);
        }

    }
}
