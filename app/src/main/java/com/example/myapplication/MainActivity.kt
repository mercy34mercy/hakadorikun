package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_page3_re.*
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_page11.*


class MainActivity : AppCompatActivity() {
    lateinit var realm: Realm
    lateinit var result: RealmResults<UserDB>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder().build()
        Realm.deleteRealm(realmConfiguration) // Delete Realm between app restarts.
        Realm.setDefaultConfiguration(realmConfiguration)
    }

    override fun onResume() {
        super.onResume()
//        realm = Realm.getDefaultInstance()
//        result = realm.where(UserDB::class.java).findAll()
//        if(result.size ==0){
//            new_user()
//            result = realm.where(UserDB::class.java).findAll()
//        }



        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
            val intent = Intent(this@MainActivity, home3::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)

        } else {
            // No user is signed in
            val intent = Intent(this@MainActivity, Login_activity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }

    fun addone(){
        realm.beginTransaction()  //開始処理
        val CntDB = realm.createObject(CntDB::class.java)
        CntDB.cnt=1
        realm.commitTransaction() //終了処理
    }

    fun new_user(){
        realm.beginTransaction()  //開始処理
        val UserDB = realm.createObject(UserDB::class.java)
        UserDB.user_email_login = false
        UserDB.user_google_login = false
        realm.commitTransaction() //終了処理
    }

}