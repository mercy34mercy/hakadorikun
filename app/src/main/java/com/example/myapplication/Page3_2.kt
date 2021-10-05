package com.example.myapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.annotation.RequiresApi
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_home3.*
import kotlinx.android.synthetic.main.activity_page3_2.*
import kotlinx.android.synthetic.main.activity_page3_re.*
import java.time.LocalDate

class Page3_2 : AppCompatActivity() {
    lateinit var realm: Realm
    lateinit var result: RealmResults<TaskDB>
    lateinit var task:TaskAdapter
    var clickcnt:Int = 0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page3_2)

        val onlyDate: LocalDate = LocalDate.now()
        val s:String = onlyDate.toString()
        val day = s.split("-")



        val m:String  = day[1].toInt().toString()
        val d:String  = day[2].toInt().toString()

        //初期状態ではbuttonは消しておく
        delete_button()

        setting_page3_2.setOnClickListener {
            val intent = Intent(this@Page3_2,Setting_activity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        home_page3_2.setOnClickListener {
            val intent = Intent(this@Page3_2,home3::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        calender_page3_2.setOnClickListener {
            val intent = Intent(this@Page3_2,page2::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        Task_not.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@Page3_2, page6::class.java)
            intent.putExtra("name","編集完了")
            intent.putExtra("Dayofmonth",d)
            intent.putExtra("month",m)
            intent.putExtra("year",day[0])
            intent.putExtra("position", position)
            startActivity(intent)
        }

        all_buttun.setOnClickListener {
            val intent = Intent(this@Page3_2,Page3_re::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        floatingActionButton_page3_2.setOnClickListener {
            if(clickcnt%2 == 0) {
                create_button()
            }else{
                delete_button()
            }
            clickcnt++
        }

        event_plus_page3_2.setOnClickListener {
            val intent = Intent(this@Page3_2, page7_re::class.java)
            intent.putExtra("Dayofmonth",d)
            intent.putExtra("month",m)
            intent.putExtra("year",day[0])
            intent.putExtra("name","追加")
            clickcnt++
            delete_button()
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        task_plus_page3_2.setOnClickListener {
            val intent = Intent(this@Page3_2, page9::class.java)
            intent.putExtra("Dayofmonth",d)
            intent.putExtra("month",m)
            intent.putExtra("year",day[0])
            intent.putExtra("name","追加")
            clickcnt++
            startActivity(intent)
            delete_button()
            overridePendingTransition(0, 0)
        }



        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val height = dm.heightPixels
        linearLayout10.layoutParams.height = height/20
    }

    private fun delete_button()
    {
        task_plus_page3_2.isClickable = false
        event_plus_page3_2.isClickable = false
        task_plus_page3_2.text = ""
        event_plus_page3_2.text = ""
        task_plus_page3_2.setBackgroundResource(R.color.clear)
        event_plus_page3_2.setBackgroundResource(R.color.clear)
    }
    private fun create_button()
    {
        task_plus_page3_2.isClickable = true
        event_plus_page3_2.isClickable = true
        task_plus_page3_2.text = "予定を追加"
        event_plus_page3_2.text = "課題を追加"
        task_plus_page3_2.setBackgroundResource(R.drawable.fillline)
        event_plus_page3_2.setBackgroundResource(R.drawable.fillline)
    }

    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()
        result = realm.where(TaskDB::class.java).lessThan("task_condition",1).findAll().sort("task_number")


            task = TaskAdapter(this, result)
            Task_not.adapter = task


    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }
}