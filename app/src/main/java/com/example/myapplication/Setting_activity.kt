package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.activity_home3.*
import kotlinx.android.synthetic.main.activity_setting.*

class Setting_activity : AppCompatActivity() {
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
}