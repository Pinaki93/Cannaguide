package dev.pinaki.cannaguide

import android.app.Application

class CannaguideApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: Application
    }
}