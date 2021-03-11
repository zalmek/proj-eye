package com.example.chargemessenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.qualifiers.ApplicationContext;

@AndroidEntryPoint
public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "RecTag";
    public static int currentlvl=0;
    Context context;
    @Inject Filereader filereader;
    @Inject Filewriter filewriter;
    @Inject MyBroadcastReceiver(@ApplicationContext Context context){
        this.context=context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        currentlvl=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
        filewriter.write("batlvl.txt",String.valueOf(currentlvl));
        if (intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN) == 2 && currentlvl==100) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s ";

            filereader.read(context.getString(R.string.bot_tokenfilename));
            String apiToken = filereader.getText();

            filereader.read(context.getString(R.string.idfilename));
            String chatId = filereader.getText() ;

            filereader.read(context.getString(R.string.textfilename));
            String text = filereader.getText();

            urlString = String.format(urlString, apiToken, chatId, text);
            Toast.makeText(context, urlString, Toast.LENGTH_SHORT).show();
            try {
                URL url = new URL(urlString);
                URLConnection conn = url.openConnection();
                InputStream is = new BufferedInputStream(conn.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
        else{
            Log.i(TAG,String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1)));
//            Toast.makeText(context, currentlvl, Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN), Toast.LENGTH_SHORT).show();

        }
    }
}




