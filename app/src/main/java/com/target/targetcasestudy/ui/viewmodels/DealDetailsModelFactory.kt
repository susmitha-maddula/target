package com.target.targetcasestudy.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.target.targetcasestudy.data.repository.DealRepository
import com.target.targetcasestudy.network.ApiService

class DealDetailsModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DealDetailsViewModel::class.java)) {
            return DealDetailsViewModel(DealRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
