package com.hds.labs.ids.hdslabs.Interface

import com.hds.labs.ids.hdslabs.model.DummyModel
import com.hds.labs.ids.hdslabs.model.UserModel
import retrofit2.Call
import retrofit2.http.*


interface ApiCall {
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("device_token") device_token: String
    ): Call<UserModel>

    @GET("api/profile")
    fun getProfile(@Query("id") id: Int?): Call<UserModel>


    @FormUrlEncoded
    @POST("api/checkin")
    fun checkIn(
        @Field("employee_id") employeeId: String,
    ): Call<UserModel>
}