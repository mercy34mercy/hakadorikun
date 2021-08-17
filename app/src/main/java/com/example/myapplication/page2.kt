package com.example.myapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_home3.*
import kotlinx.android.synthetic.main.activity_page11.*
import kotlinx.android.synthetic.main.activity_page2.*
import kotlinx.android.synthetic.main.activity_page3_re.*
import java.time.LocalDate

class page2 : AppCompatActivity() {
    var clickcnt:Int = 0
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
            overridePendingTransition(0, 0)
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

        floatingActionButton_page2.setOnClickListener {
            if(clickcnt%2 == 0) {
                task_plus_page2.isClickable = true
                event_plus_page2.isClickable = true

                task_plus_page2.text = "予定を追加"
                event_plus_page2.text = "課題を追加"
                task_plus_page2.setBackgroundResource(R.drawable.fillline)
                event_plus_page2.setBackgroundResource(R.drawable.fillline)
            }else{
                task_plus_page2.isClickable = false
                event_plus_page2.isClickable = false
                task_plus_page2.text = ""
                event_plus_page2.text = ""
                task_plus_page2.setBackgroundResource(R.color.clear)
                event_plus_page2.setBackgroundResource(R.color.clear)
            }
            clickcnt++
        }

        event_plus_page2.setOnClickListener {
            val intent = Intent(this@page2, page7_re::class.java)
            intent.putExtra("Dayofmonth",d)
            intent.putExtra("month",m)
            intent.putExtra("year",day[0])
            intent.putExtra("name","追加")
            startActivity(intent)
            clickcnt++
            overridePendingTransition(0, 0)
        }

        task_plus_page2.setOnClickListener {
            val intent = Intent(this@page2, page9::class.java)
            intent.putExtra("Dayofmonth",d)
            intent.putExtra("month",m)
            intent.putExtra("year",day[0])
            intent.putExtra("name","追加")
            startActivity(intent)
            clickcnt++
            overridePendingTransition(0, 0)
        }

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val height = dm.heightPixels
        liner_home_page2.layoutParams.height = height/20
    }
}