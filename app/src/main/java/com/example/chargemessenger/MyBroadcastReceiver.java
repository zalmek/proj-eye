package com.example.chargemessenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.chargemessenger.Network.Network;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.qualifiers.ApplicationContext;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@AndroidEntryPoint
public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "RecTag";
    public static int currentlvl = 0;
    public String CHatid ="";
    Context context;
    @Inject
    Filereader filereader;
    @Inject
    Filewriter filewriter;
    @Inject
    Network network;


    @Inject
    MyBroadcastReceiver(@ApplicationContext Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        currentlvl = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        filewriter.write("batlvl.txt", String.valueOf(currentlvl));
        if (intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN) == 2 && currentlvl == 100) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String urlString = "https://api.telegram.org/bot1448041949:AAGKZXLqa7MTi25uE3JflofJrFadzY0KQSc/sendMessage?chat_id=%s&text=full_charge ";
//iuh
            File f = new File(context.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath()+ "/"+context.getString(R.string.idfilename));
            if (!f.exists()){
                IThread iTheread = new IThread();
                iTheread.start();}
            filereader.read(context.getString(R.string.idfilename));
            String chatId = filereader.getText();
            if (chatId.length() < 5){
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        User user= (User) msg.obj;
                        try {
                            CHatid = String.valueOf(user.getUserid());
                        }
                        catch (Exception e){
                        }
                        IThread iThread = new IThread();
                        iThread.start();
                    }
                };
                network.getOne(handler);
            }
            urlString = String.format(urlString, chatId);
            try {
                run(urlString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)));
//            Toast.makeText(context, currentlvl, Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN), Toast.LENGTH_SHORT).show();

        }
    }

    String run(String url) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }
    class IThread extends Thread {
        @Override
        public void run()    //Этот метод будет выполнен в побочном потоке
        {
            filewriter.write(context.getString(R.string.idfilename),CHatid);
        }
    }
}




