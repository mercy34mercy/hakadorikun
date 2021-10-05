package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
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
            edit_hour_setting.setText(user_result[0]!!.tuuti_hour.toString())
            edit_time_setting.setText(user_result[0]!!.tuuti_minute.toString())
        }

        set_account_setting.setOnClickListener {
            set_user(user_result)
        }






    }

    fun set_user(user_p:RealmResults<UserDB>)
    {
        if(edit_hour_setting.text.isNullOrEmpty() || edit_time_setting.text.isNullOrEmpty() || text_email_setting.text.isNullOrEmpty() || text_gakubu_setting.text.isNullOrEmpty() || text_gakka_setting.text.isNullOrEmpty() || text_gakunen_setting.text.isNullOrEmpty() || text_name_setting.text.isNullOrEmpty()){
            val context = getApplicationContext();
            Toast.makeText(context, "項目が不十分です", Toast.LENGTH_LONG).show();
        }else if(user_p.size != 0){
            user_p[0]!!.mailaddress = text_email_setting.text.toString()
            user_p[0]!!.gakubu = text_gakubu_setting.text.toString()
            user_p[0]!!.gakka = text_gakka_setting.text.toString()
            user_p[0]!!.grade = text_gakunen_setting.text.toString()
            user_p[0]!!.nickname  = text_name_setting.text.toString()
            val context = getApplicationContext();
            Toast.makeText(context, "情報が作成されました", Toast.LENGTH_LONG).show();
        }else{
            realm.beginTransaction()  //開始処理
            val userDB = realm.createObject(UserDB::class.java)
            userDB.gakubu      = text_gakubu_setting.text.toString()
            userDB.gakka       = text_gakka_setting.text.toString()
            userDB.mailaddress = text_email_setting.text.toString()
            userDB.grade = text_gakunen_setting.text.toString()
            userDB.nickname = text_name_setting.text.toString()
            val context = getApplicationContext();
            Toast.makeText(context, "情報が更新されました", Toast.LENGTH_LONG).show();

        }

    }

    fun set_tuuti(tuuti_p:RealmResults<UserDB>){
        if(edit_hour_setting.text.isNullOrEmpty() || edit_time_setting.text.isNullOrEmpty()){
            val context = getApplicationContext();
            Toast.makeText(context, "項目が不十分です", Toast.LENGTH_LONG).show();
        }else if(tuuti_p.size != 0){
            val hour :Int  = edit_hour_setting.text.toString().toInt()
            val minute:Int = edit_time_setting.text.toString().toInt()
            tuuti_p[0]!!.tuuti_hour = hour
            tuuti_p[0]!!.tuuti_minute = minute

            val context = getApplicationContext();
            Toast.makeText(context, "情報が作成されました", Toast.LENGTH_LONG).show();
        }else{
            realm.beginTransaction()  //開始処理
            val userDB = realm.createObject(UserDB::class.java)
            val hour :Int  = edit_hour_setting.text.toString().toInt()
            val minute:Int = edit_time_setting.text.toString().toInt()

            userDB.tuuti_hour     = hour
            userDB.tuuti_minute   = minute

            val context = getApplicationContext();
            Toast.makeText(context, "情報が更新されました", Toast.LENGTH_LONG).show();

        }

    }

    override fun onPause() {
        super.onPause()
        realm.close()


    }
}