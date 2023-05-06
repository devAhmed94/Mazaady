package com.example.mazaady.app.di

import com.example.mazaady.BuildConfig
import com.example.mazaady.data.network.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Ahmed Ali Ebaid
 * ahmedali26002844@gmail.com
 * 04/05/2023
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        fun headerInterceptor(): Interceptor {
            return Interceptor {
                val request = it.request().newBuilder()
                    .header("Accept-Language", "en")
                    .header("private-key", "3%o8i}_;3D4bF]G5@22r2)Et1&mLJ4?\$@+16")
                    .build()

                it.proceed(request)
            }
        }

        fun loggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }
        }
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor())
            .addInterceptor(loggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://staging.mazaady.com/api/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

}