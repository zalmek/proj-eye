package com.example.chargemessenger.Network;

import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.chargemessenger.Filereader;
import com.example.chargemessenger.R;
import com.example.chargemessenger.User;

import javax.inject.Inject;

import dagger.Provides;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    Retrofit retrofit;
    API api;
    @Inject
    Filereader filereader;
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
        filereader.read("text.txt");
        api.getOne(filereader.getText()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Message msg = new Message();
                msg.obj=response.body();
                Log.i(TAG,"smth");
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                Log.i(TAG,"Failure");
            }
        });

    }
}
