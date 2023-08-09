package com.samiun.businesskitchen.data.model

data class Data(
    var cakeItems: List<Items> = listOf(),
    var consumerGoodItems: List<Items> = listOf(),
    var miscItems: List<Items> = listOf(),
    var fastFoodItems: List<Items> = listOf(),
)
