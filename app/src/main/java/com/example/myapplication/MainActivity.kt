package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        realm = Realm.getDefaultInstance()
        result = realm.where(UserDB::class.java).findAll()





        var email = result[0]?.user_email_address
        var email_size:Int = 0
        if (email != null) {
            email_size = email.length
        }else{
            new_user()
            email = result[0]?.user_email_address
            if (email != null) {
                email_size = email.length
            }
        }

            if(email_size < 7 ){
                val intent = Intent(this@MainActivity, Login_activity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)


            }else if(result[0]!!.user_google_login == true) {
                val intent = Intent(this@MainActivity, home3::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }else
            {
                val intent = Intent(this@MainActivity, home3::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)

            }

    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }

    fun new_user(){
        realm.beginTransaction()  //開始処理
        val UserDB = realm.createObject(UserDB::class.java)
        UserDB.user_email_address = "sample"
        UserDB.user_password = "sample"
        UserDB.user_google_login = false
        UserDB.user_id = "sample"
        realm.commitTransaction() //終了処理
    }

}