package jp.masashi.hakadori

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class Myapplication:Application() {
    override fun onCreate() {
        super.onCreate()
        //realmの初期化



        Realm.init(this)

    }
}