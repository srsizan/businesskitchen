package com.samiun.businesskitchen.ui.screens

import com.samiun.businesskitchen.R

sealed class Screen(val route: String) {
    object SigningScreen : Screen("sign_in")
    object ProfileScreen : Screen("profile")
    object CakeScreen : Screen("Cake")
    object AddCakeScreen : Screen("Add Cake")
    object ConsumerGoodsScreen : Screen("Consumer Goods")
    object AddConsumerGoodsScreen : Screen("Add Consumer Goods")
    object MiscScreen : Screen("Misc")
    object AddMiscScreen : Screen("Add Misc")
    object FastFoodScreen : Screen("Fast Food")
    object AddFastFoodScreen : Screen("Add Fast Food")
}