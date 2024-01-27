package com.example.homework_3

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaApi {

    @GET("api/")
    fun getImages(
        @Query("key") key: String = "42040186-d207638d6d1b1cb43e96acc07",
        @Query("q") keyWordForSearch: String,
        @Query("per_page") perPage: Int = 3,
        @Query("page") page: Int = 1,
    ): Call<PixabayModel>
}
//https://pixabay.com/api/
//https://pixabay.com/api/?key=42040186-d207638d6d1b1cb43e96acc07&q=yellow+flowers&image_type=photo&pretty=true