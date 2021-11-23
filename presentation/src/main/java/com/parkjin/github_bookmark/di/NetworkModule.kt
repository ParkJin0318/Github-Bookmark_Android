package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.network.api.UserAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val DEFAULT_HOST = "https://api.github.com/"
private const val TIME_OUT = 20

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addInterceptor(interceptor)
            .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS).build()
    }

    @Provides
    fun provideRetrofitBuilder(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(DEFAULT_HOST)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    @Provides
    fun provideUserApi(retrofit: Retrofit): UserAPI = retrofit.create(UserAPI::class.java)
}
