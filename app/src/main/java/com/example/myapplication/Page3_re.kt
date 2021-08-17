package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.Gravity
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.scaleMatrix
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_page11.*
import kotlinx.android.synthetic.main.activity_page2.*
import kotlinx.android.synthetic.main.activity_page3_re.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import retrofit2.Retrofit
import java.time.LocalDate

class Page3_re : AppCompatActivity() {
    lateinit var realm:Realm
    lateinit var result:RealmResults<TaskDB>
    lateinit var task:TaskAdapter
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page3_re)

        val onlyDate: LocalDate = LocalDate.now()
        val s:String = onlyDate.toString()
        val day = s.split("-")

        val m:String  = day[1].toInt().toString()
        val d:String  = day[2].toInt().toString()


        home_tab.setOnClickListener {
            val intent = Intent(this@Page3_re,home3::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        calender_tab.setOnClickListener {
            val intent = Intent(this@Page3_re,page2::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        task_list.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@Page3_re, page6::class.java)
            intent.putExtra("name","編集完了")
            intent.putExtra("Dayofmonth",d)
            intent.putExtra("month",m)
            intent.putExtra("year",day[0])
            intent.putExtra("position", position)
            startActivity(intent)
        }

        add_biutton_page3.setOnClickListener {
            val intent = Intent(this@Page3_re,page7_re::class.java)
            intent.putExtra("name","追加")
            intent.putExtra("Dayofmonth",d)
            intent.putExtra("month",m)
            intent.putExtra("year",day[0])
            startActivity(intent)
        }

        //右下のプラスボタンその１
//        sub_btn1_home3.setOnClickListener {
//            val intent = Intent(this@Page3_re, page7_re::class.java)
//            intent.putExtra("subject","")
//            intent.putExtra("Dayofmonth",d)
//            intent.putExtra("month",m)
//            intent.putExtra("year",day[0])
//            intent.putExtra("name","追加")
//            startActivity(intent)
//        }
//
//        //右下のプラスボタンその２
//        sub_btn2.setOnClickListener {
//
//            val intent = Intent(this@Page3_re, page9::class.java)
//            intent.putExtra("Dayofmonth",d)
//            intent.putExtra("month",m)
//            intent.putExtra("year",day[0])
//            intent.putExtra("name","追加")
//            startActivity(intent)
//        }


   }

    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()
        result = realm.where(TaskDB::class.java)
            .sort("task_number").findAll()


        task = TaskAdapter(this,result)
        task_list.adapter = task


    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }


}