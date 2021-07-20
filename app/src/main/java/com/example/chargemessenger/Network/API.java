package com.example.chargemessenger.Network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @GET("User/{uuid}")
    Call<Integer> getUserId(@Path("uuid") String uuid);
    @GET("sendMessage")
    Call<Object> getObject(@Query("chat_id") String Userid,
                           @Query("text") String full_charge);
}
//chat_id=Userid&text=full_charge