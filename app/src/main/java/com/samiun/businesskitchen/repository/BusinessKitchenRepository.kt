package com.samiun.businesskitchen.repository

import com.samiun.businesskitchen.data.remote.LoginModel
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class BusinessKitchenRepository @Inject constructor(

) {
    suspend fun postLogin(loginModel: LoginModel) = {

    }

}