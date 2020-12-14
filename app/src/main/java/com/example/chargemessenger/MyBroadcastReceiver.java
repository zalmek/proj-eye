package com.example.chargemessenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
        if (intent.getIntExtra(BatteryManager.EXTRA_STATUS,BatteryManager.BATTERY_STATUS_UNKNOWN) == 5) {
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
            Toast.makeText(context, contents, Toast.LENGTH_SHORT).show();
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
            } catch (Exception e) {
                e.printStackTrace();
            }


    }
        else Log.i(TAG,String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_STATUS,BatteryManager.BATTERY_STATUS_UNKNOWN)));
        }

    }



