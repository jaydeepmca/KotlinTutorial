package com.example.kotlintutorial

import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    @Headers("AUTH_KEY: 27375a7bf4992af9394d3dabb74acc14")
    @GET("MobileAppLogin?")
    fun getMovies(
        @Query("USERCODE") USERCODE: String,
        @Query("PASSWORD") PASSWORD: String,
        @Query("ACTION") ACTION: Int
    ): Call<LoginResponseModel>

    @FormUrlEncoded
    @POST("InsertScandata")
    @Headers("Content-Type: application/json", "AUTH_KEY: 27375a7bf4992af9394d3dabb74acc14")
    fun uploadImage(@Body request: ImageUploadReq?): Call<ImageUploadReq>
}