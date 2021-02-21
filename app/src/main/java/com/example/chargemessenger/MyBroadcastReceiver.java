package com.example.chargemessenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "RecTag";
    @Inject Filereader filereader;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN) == 5) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s ";

            filereader.read(EnterFragment.bot_token_file);
            String apiToken = filereader.getText();

            filereader.read(EnterFragment.id_file);
            String chatId = filereader.getText() ;

            filereader.read(EnterFragment.text_file);
            String text = filereader.getText();

            urlString = String.format(urlString, apiToken, chatId, text);

            try {
                URL url = new URL(urlString);
                URLConnection conn = url.openConnection();
                InputStream is = new BufferedInputStream(conn.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
        else Log.i(TAG,String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1)));
        }
}




