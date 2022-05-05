package com.example.lab_4_todo_app

import android.app.Application
import androidx.room.Room
import com.example.lab_4_todo_app.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MyApplication: Application() {
    private var database: AppDatabase? = null
    private var apiService: ApiService? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "todo_task_app_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun getDataBase(): AppDatabase? {
        return database
    }

    fun getApiService(): ApiService? {
        return apiService
    }

    companion object {
        lateinit var instance: MyApplication
    }
}