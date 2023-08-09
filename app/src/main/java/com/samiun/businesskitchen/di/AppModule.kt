package com.samiun.businesskitchen.di

import android.content.Context
import android.content.SharedPreferences
import com.samiun.businesskitchen.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSettingsRepository(sharedPreferences: SharedPreferences) =
        SettingsRepository(sharedPreferences)

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("BUSINESS_KITCHEN_PREF", Context.MODE_PRIVATE)
    }
}