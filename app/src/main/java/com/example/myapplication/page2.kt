package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_page2.*

class page2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page2)

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
//            val intent = Intent(this@page2,::class.java)
//            startActivity(intent)
        }
    }
}