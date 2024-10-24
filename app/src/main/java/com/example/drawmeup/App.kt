package com.example.drawmeup

import android.app.Application
import com.example.drawmeup.data.RepositoryLocator

class App :Application() {
    override fun onCreate() {
            super.onCreate()
            RepositoryLocator.init(applicationContext)
    }
}