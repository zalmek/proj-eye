package com.example.chargemessenger.Network;

import com.example.chargemessenger.Database.ConfigRepository;
import com.example.chargemessenger.MVVM.ViewModel.ConfigViewModel;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class OpenTelUrlNetwork {
    Retrofit retrofit;
    API api;
    @Inject
    ConfigViewModel mViewModel;
    private static final String TAG = "LogNetwork";

    @Inject
    public OpenTelUrlNetwork() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.telegram.org/bot1448041949:AAGKZXLqa7MTi25uE3JflofJrFadzY0KQSc/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.api = retrofit.create(API.class);

    }

    public void openUrl(int currentlvl) {
        if (mViewModel.getConfig().getValue() != null) {
            api.getObject(mViewModel.getConfig().getValue().getUserid().toString(),String.valueOf(currentlvl)).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
