package com.bgargarella.ram.di

import com.bgargarella.ram.data.BuildConfig
import com.bgargarella.ram.data.api.APIService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class NetworkModule {

    @Singleton
    private val interceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) BODY else NONE
        }

    @Singleton
    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @Singleton
    private val moshi: Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Singleton
    private val factory: MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Singleton
    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(factory)
            .build()

    @Singleton
    protected val service: APIService = retrofit.create(APIService::class.java)
}