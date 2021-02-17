package com.target.targetcasestudy.data.model

data class DealItem(
    var id: Int,
    var title: String,
    var description: String,
    var regular_price: PriceModel,
    var sale_price: PriceModel?,
    var aisle: String,
    var image_url: String?
)
