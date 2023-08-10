package com.samiun.businesskitchen.data.model

data class Items(
    val name: String = "",
    val quantity: Int = 0,
    val category: String = "",
    val image: String = "",
    val maxUsage: Int = 0,
    val maxStock: Int = 0,
)