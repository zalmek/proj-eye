package com.example.chargemessenger.Network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.chargemessenger.Database.ConfigRepository;
import com.example.chargemessenger.MVVM.ViewModel.ConfigViewModel;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    Retrofit retrofit;
    API api;
    @Inject
    ConfigViewModel mViewModel;
    private static final String TAG = "LogNetwork";
    @Inject
    public Network() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://chargemessenger.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.api = retrofit.create(API.class);
    }

    public void getOne(Handler handler){
        if (mViewModel.getConfig().getValue() != null) {
            api.getUserId(mViewModel.getConfig().getValue().getUuid()).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    Message msg = new Message();
                    msg.obj = response.body();
                    handler.sendMessage(msg);
                    Log.i(TAG, "smth");
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    t.printStackTrace();
                    Log.i(TAG, "Failure");
                }
            });
        }
    }
}
