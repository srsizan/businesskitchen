package com.samiun.businesskitchen.ui

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object MainMenuScreen : Screen("main_menu_screen")
    object CagesScreen : Screen("cages_screen")
    object ConsumerGoodsScreen : Screen("consumer_goods_screen")
    object MiscScreen : Screen("misc_screen")
    object FastFoodScreen : Screen("fast_food_screen")
    object ControlPanelScreen : Screen("control_panel_screen")
}