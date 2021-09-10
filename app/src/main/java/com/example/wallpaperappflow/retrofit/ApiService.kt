package com.example.wallpaperappflow.retrofit


import com.example.wallpaperappflow.model.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api")
    fun getImages(

        @Query("key") key: String,
        @Query("q") q: String,        @Query("page") page: Int,
        @Query("per_page") per_page: Int=15


    ): Call<Photo>
}