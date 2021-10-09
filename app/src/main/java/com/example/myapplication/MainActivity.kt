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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder().build()
        //Realm.deleteRealm(realmConfiguration) // Delete Realm between app restarts.
        Realm.setDefaultConfiguration(realmConfiguration)
    }

    override fun onResume() {
        super.onResume()




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

    }

}