package com.samiun.businesskitchen.presentation.sign_in

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String = "",
    val username: String?= "",
    val profilePictureUrl: String? = null,
    val email: String?,
    val canAccess: Boolean = false
)
