package com.target.targetcasestudy.data.repository

import com.target.targetcasestudy.network.ApiService

class DealRepository constructor(val apiService: ApiService) {

    suspend fun getProducts() = apiService.getProducts()

    suspend fun getDealDetails(id: Int) = apiService.getDealDetails(id)

}
