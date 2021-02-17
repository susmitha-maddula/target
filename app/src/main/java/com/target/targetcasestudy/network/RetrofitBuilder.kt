package com.target.targetcasestudy.network

import com.google.gson.GsonBuilder
import com.target.targetcasestudy.data.api.DealDetailsDeserializer
import com.target.targetcasestudy.data.api.DealDetailsResponseModel
import com.target.targetcasestudy.utils.TargetConstants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().registerTypeAdapter(DealDetailsResponseModel::class.java, DealDetailsDeserializer())
                        .create()
                )
            )
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

}
