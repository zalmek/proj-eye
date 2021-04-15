package com.example.chargemessenger.Network;

import com.example.chargemessenger.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {
    @GET("User/{uuid}")
    Call<User> getOne(@Path("uuid") String uuid);

}
