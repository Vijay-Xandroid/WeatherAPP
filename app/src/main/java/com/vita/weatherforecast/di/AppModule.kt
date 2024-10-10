package com.vita.weatherforecast.di

import android.app.Application
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.vita.weatherforecast.data.local.WeatherAppDatabase
import com.vita.weatherforecast.data.remote.Constants
import com.vita.weatherforecast.data.remote.WeatherApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    private val moshi: Moshi? = null

    private fun provideMoshi(): Moshi {
        synchronized(this) {
            return moshi ?: Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        }
    }

    private fun getClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApi() : WeatherApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(getClient())
            .build()
            .create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherDatabase(app: Application) : WeatherAppDatabase {
        return Room.databaseBuilder(
            app,
            WeatherAppDatabase::class.java,
            "weatherappdb.db",
        ).build()
    }
}