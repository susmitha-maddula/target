package com.target.targetcasestudy.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.target.targetcasestudy.data.model.DealItem
import com.target.targetcasestudy.data.repository.DealRepository
import com.target.targetcasestudy.utils.Resource
import kotlinx.coroutines.Dispatchers


class DealListViewModel(private val dealRepository: DealRepository) : ViewModel() {

    fun getDeals() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = dealRepository.getProducts()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
