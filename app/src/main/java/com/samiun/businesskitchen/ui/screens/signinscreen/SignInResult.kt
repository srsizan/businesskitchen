package com.samiun.businesskitchen.ui.screens.signinscreen

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val canAccess: Boolean = false,
    val userId: String,
    val userEmail: String?,
    val username: String?,
    val profilePictureUrl: String?
)
