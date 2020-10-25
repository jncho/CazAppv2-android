package com.greatideas.cazapp

import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.log.LogLevel
import io.realm.log.RealmLog
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration

lateinit var cazAppDB : App
lateinit var localRealm: Realm

inline fun <reified T> T.TAG(): String = T::class.java.simpleName

class CazApp : Application(){

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        // App Mongodb
        cazAppDB = App(
            AppConfiguration.Builder(BuildConfig.MONGODB_REALM_APP_ID)
                .build())

        // Realm non-synced database
        val config = RealmConfiguration.Builder().allowWritesOnUiThread(true).build()
        Realm.setDefaultConfiguration(config)
        localRealm = Realm.getDefaultInstance()

        // Enable more logging in debug mode
        if (BuildConfig.DEBUG) {
            RealmLog.setLevel(LogLevel.ALL)
        }

        Log.v(TAG(), "Initialized the Realm App configuration for: ${cazAppDB.configuration.appId}")
    }

    override fun onTerminate() {
        super.onTerminate()
        localRealm.close()
    }

}