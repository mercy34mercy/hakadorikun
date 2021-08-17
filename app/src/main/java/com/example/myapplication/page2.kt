package com.example.myapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_page11.*
import kotlinx.android.synthetic.main.activity_page2.*
import java.time.LocalDate

class page2 : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page2)

        val onlyDate: LocalDate = LocalDate.now()
        val s:String = onlyDate.toString()
        val day = s.split("-")

        val m:String  = day[1].toInt().toString()
        val d:String  = day[2].toInt().toString()

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->



            val intent = Intent(this@page2,page5_re::class.java)
            intent.putExtra("Dayofmonth",dayOfMonth.toString())
            intent.putExtra("month",(month+1).toString())
            intent.putExtra("year",year.toString())
            startActivity(intent)
        }

        home_button_page2.setOnClickListener {
            val intent = Intent(this@page2, home3::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        task_button_page2.setOnClickListener {
            val intent = Intent(this@page2,Page3_re::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        setting_button_page2.setOnClickListener {
//            val intent = Intent(this@page2,Page12::class.java)
//            startActivity(intent)
        }

        //右下のプラスボタンその１
//        sub_btn1_home3.setOnClickListener {
//            val intent = Intent(this@page2, page7_re::class.java)
//            intent.putExtra("subject","")
//            intent.putExtra("Dayofmonth",d)
//            intent.putExtra("month",m)
//            intent.putExtra("year",day[0])
//            intent.putExtra("name","追加")
//            startActivity(intent)
//        }
//
//        //右下のプラスボタンその２
//        sub_btn2_home3.setOnClickListener {
//
//            val intent = Intent(this@page2, page9::class.java)
//            intent.putExtra("Dayofmonth",d)
//            intent.putExtra("month",m)
//            intent.putExtra("year",day[0])
//            intent.putExtra("name","追加")
//            startActivity(intent)
//        }
    }
}