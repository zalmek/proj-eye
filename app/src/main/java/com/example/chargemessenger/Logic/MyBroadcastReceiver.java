package com.example.chargemessenger.Logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.chargemessenger.Database.ConfigRepository;
import com.example.chargemessenger.MVVM.ViewModel.ConfigViewModel;
import com.example.chargemessenger.Network.Network;
import com.example.chargemessenger.Network.OpenTelUrlNetwork;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.qualifiers.ApplicationContext;

@AndroidEntryPoint
public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "RecTag";
    public static int currentlvl = 0;
    public String CHatid ="";
    Context context;
    @Inject
    ConfigViewModel mViewModel;
    @Inject
    Network network;
    @Inject
    OpenTelUrlNetwork openTelUrlNetwork;


    @Inject
    MyBroadcastReceiver(@ApplicationContext Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        currentlvl = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        mViewModel.ChangeConfig(null, null, currentlvl);

        if (intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN) == 2 && currentlvl == 100 || intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN) == 5) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String urlString = "https://api.telegram.org/bot1448041949:AAGKZXLqa7MTi25uE3JflofJrFadzY0KQSc/sendMessage?chat_id=%s&text=full_charge ";

            if (mViewModel.getConfig().getValue() != null) {
                if (mViewModel.getConfig().getValue().getUserid().toString().length() < 5) {
                    Handler handler = new Handler() {
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            Integer userId = (Integer) msg.obj;
                            mViewModel.ChangeConfig(null, userId, null);
                        }
                    };
                    network.getOne(handler);
                }
            }
            openTelUrlNetwork.openUrl();
            }
        }
        }




