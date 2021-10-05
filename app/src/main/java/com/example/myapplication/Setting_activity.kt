package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_home3.*
import kotlinx.android.synthetic.main.activity_setting.*

class Setting_activity : AppCompatActivity() {
    lateinit var realm: Realm
    lateinit var user_result : RealmResults<UserDB>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val height = dm.heightPixels
        val width = dm.widthPixels

        select_Setting.layoutParams.height = height/20

        home_setting.setOnClickListener {
            val intent = Intent(this@Setting_activity,home3::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        calender_setting.setOnClickListener {
            intent = Intent(this@Setting_activity, page2::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        task_setting.setOnClickListener {
            intent = Intent(this@Setting_activity, Page3_re::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        set_account_setting.setOnClickListener {
            TODO("ここで登録を変更する")
        }
    }


    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()
        user_result = realm.where(UserDB::class.java).findAll()
        val length = user_result.size
        if (length != 0){
            text_email_setting.setText(user_result[0]!!.mailaddress)
            text_gakubu_setting.setText(user_result[0]!!.gakubu)
            text_gakka_setting.setText(user_result[0]!!.gakka)
            text_gakunen_setting.setText(user_result[0]!!.grade)
        }




    }

    override fun onPause() {
        super.onPause()
        realm.close()


    }
}