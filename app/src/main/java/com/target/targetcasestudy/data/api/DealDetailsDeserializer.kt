package com.target.targetcasestudy.data.api

import com.google.gson.*
import com.target.targetcasestudy.data.model.DealItem
import java.lang.reflect.Type


class DealDetailsDeserializer : JsonDeserializer<DealDetailsResponseModel> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DealDetailsResponseModel {
        var dealDetailsResponseModel: DealDetailsResponseModel = DealDetailsResponseModel()
        json?.let {
            val jobject = it.asJsonObject

            val gson = Gson()
            if (jobject.has("message")) {
                dealDetailsResponseModel.itemNotFoundModel = gson.fromJson(jobject, ItemNotFoundModel::class.java)
            } else {
                dealDetailsResponseModel.product = gson.fromJson(jobject, DealItem::class.java)
            }
        }
        return dealDetailsResponseModel
    }
}
