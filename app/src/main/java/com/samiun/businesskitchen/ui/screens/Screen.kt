package com.samiun.businesskitchen.ui.screens

sealed class Screen(val route: String) {
    object SigningScreen : Screen("sign_in")
    object HomeScreen : Screen("home_screen")
    object HomeScreenLoggedIn : Screen("loged_home_screen")
    object CakeScreen : Screen("Cake")
    object AddCakeScreen : Screen("Add Cake")
    object ConsumerGoodsScreen : Screen("Consumer Goods")
    object AddConsumerGoodsScreen : Screen("Add Consumer Goods")
    object MiscScreen : Screen("Misc")
    object AddMiscScreen : Screen("Add Misc")
    object FastFoodScreen : Screen("Fast Food")
    object AddFastFoodScreen : Screen("Add Fast Food")
    object AddScreen : Screen("Add Item")
}