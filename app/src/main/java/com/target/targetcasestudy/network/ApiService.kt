package com.target.targetcasestudy.network

import com.target.targetcasestudy.data.api.DealDetailsResponseModel
import com.target.targetcasestudy.data.model.Products
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("deals")
    suspend fun getProducts(): Products

    @GET("deals/{id}")
    suspend fun getDealDetails(@Path("id") id: Int): DealDetailsResponseModel

}
