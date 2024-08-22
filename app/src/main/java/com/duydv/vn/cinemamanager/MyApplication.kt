package com.duydv.vn.cinemamanager

import android.app.Application
import android.content.Context
import com.duydv.vn.cinemamanager.prefs.DataStoreManager
import com.google.firebase.FirebaseApp

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DataStoreManager.init(applicationContext)
        FirebaseApp.initializeApp(this)
    }

    companion object {
        //private const val FIREBASE_URL = "https://cinema-c68f6-default-rtdb.firebaseio.com"
        operator fun get(context: Context?): MyApplication {
            return context!!.applicationContext as MyApplication
        }
    }
}