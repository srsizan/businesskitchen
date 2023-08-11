package com.samiun.businesskitchen.data.model

data class Items(
    var name: String = "",
    var quantity: Int = 0,
    var category: String = "",
    var image: String = "",
    var maxUsage: Int = 0,
    var maxStock: Int = 0,
)