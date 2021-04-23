package com.example.chargemessenger.Network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {
    @GET("User/{uuid}")
    Call<Integer> getUserId(@Path("uuid") String uuid);
//    @GET("bot1448041949:AAGKZXLqa7MTi25uE3JflofJrFadzY0KQSc/sendMessage?chat_id={Userid}&text=full_charge")
//    Call<>
}
