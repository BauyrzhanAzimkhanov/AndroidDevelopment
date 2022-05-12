package com.example.Project

import android.app.Application
import com.example.Project.projectDependencyInjection.appModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)    //
        startKoin {
            androidContext(this@App)    //
            modules(appModule)    // we inject dependencies from appModule
        }
    }
}