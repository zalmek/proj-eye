package com.example.chargemessenger.Network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.chargemessenger.Database.ConfigRepository;

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
    ConfigRepository repository;
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
        api.getUserId(repository.getConfig().getUuid()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Message msg = new Message();
                msg.obj=response.body();
                Log.i(TAG,"smth");
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                t.printStackTrace();
                Log.i(TAG,"Failure");
            }
        });

    }
}
