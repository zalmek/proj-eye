package com.example.chargemessenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MyBroadcastReceiver extends BroadcastReceiver {
    Boolean charger=false;
    private static final String TAG = "RecTag";
    @Override
    public void onReceive(Context context, Intent intent) {// +- работает
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
        // Не уверен что нужны эти 3 строки но если вдруг понадабятся - раскомментируйте!
//        Intent notify = new Intent(context, MainActivity.class);
//        Notification noti;
//        PendingIntent pIntent = PendingIntent.getActivity(context, 0, notify, 0);

//        if(curLevel <= 15 && !charger) {
//            noti = new Notification.Builder(context).setTicker("Ticker Title")
//                    .setContentTitle("Battery Low")
//                    .setContentText("Battery " + curLevel + "% Please Connect Charger")
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentIntent(pIntent).getNotification();
//            noti.flags = Notification.FLAG_AUTO_CANCEL;
//            NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//            nm.notify(0, noti);
//        }
         if(curLevel == 100 && charger){
             // try to read file
             String rootDataDir = context.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();
             File file = new File(rootDataDir, "id.txt");
             Log.i(TAG, rootDataDir.toString());
             int length = (int) file.length();

             byte[] bytes = new byte[length];

             FileInputStream in = null;
             try {
                 in = new FileInputStream(file);
             } catch (FileNotFoundException e) {
                 e.printStackTrace();
             }
             try {
                 in.read(bytes);
             } catch (IOException e) {
                 e.printStackTrace();
             } finally {
                 try {
                     in.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
             // send message
             String contents = new String(bytes);
             Toast.makeText(context,contents,Toast.LENGTH_SHORT).show();
             StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

             StrictMode.setThreadPolicy(policy);
             String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s ";

             String apiToken = "1448041949:AAGKZXLqa7MTi25uE3JflofJrFadzY0KQSc";

             // really need a func
             String chatId = contents;
             String text = "Full_charge";

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
        }

    }
}
