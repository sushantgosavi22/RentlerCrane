package com.sushant.sampledemomvvmapicall.application

import android.app.Application
import android.content.Context
import com.sushant.sampledemomvvmapicall.di.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        appComponent = initDaggerComponent()
    }


    companion object {
        private lateinit var appComponent: AppComponent
        var appContext: Context? = null
            private set

        fun getAppComponent(): AppComponent {
            return appComponent
        }
    }

    private fun initDaggerComponent(): AppComponent {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule)
            .repositoryModule(RepositoryModule)
            .build()
        return appComponent

    }
}