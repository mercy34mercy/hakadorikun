package com.example.myapplication

import android.app.Application
import io.realm.Realm

class Myapplication:Application() {
    override fun onCreate() {
        super.onCreate()
        //realmの初期化
        Realm.init(this)
    }
}