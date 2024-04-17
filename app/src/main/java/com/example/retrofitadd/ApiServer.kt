package com.example.retrofitadd

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServer {
    @POST("/comments")
    fun AddItem(@Body data:Datatype): Call<Datatype>
}