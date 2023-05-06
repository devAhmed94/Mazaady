package com.example.mazaady.data.network

import com.example.mazaady.domain.entities.ResCategory
import com.example.mazaady.domain.entities.ResProps
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Ahmed Ali Ebaid
 * ahmedali26002844@gmail.com
 * 04/05/2023
 */
interface ApiServices {
    @GET("get_all_cats")
    suspend fun getCategories(): ResCategory

    @GET("properties")
    suspend fun getProps(@Query("cat") cat: Int): ResProps
}