package com.target.targetcasestudy.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.target.targetcasestudy.data.api.DealDetailsResponseModel
import com.target.targetcasestudy.data.repository.DealRepository
import com.target.targetcasestudy.utils.Resource
import kotlinx.coroutines.Dispatchers

class DealDetailsViewModel(private val dealRepository: DealRepository) : ViewModel() {

    fun getDealDetails(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            var dealDetailsResponseModel: DealDetailsResponseModel = dealRepository.getDealDetails(id)
            if (dealDetailsResponseModel.product != null) {
                emit(Resource.success(data = dealDetailsResponseModel.product))
            } else {
                emit(
                    Resource.error(
                        data = null,
                        message = dealDetailsResponseModel.itemNotFoundModel?.message ?: "Error Occurred!"
                    )
                )
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
