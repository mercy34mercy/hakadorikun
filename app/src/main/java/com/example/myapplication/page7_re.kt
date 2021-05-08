package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.NumberPicker
import android.widget.TextView
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_page7_re.*
import kotlin.math.min

class page7_re : AppCompatActivity() {
    lateinit var realm_page7: Realm
    lateinit var result: RealmResults<TaskDB>
    lateinit var end_time:String
    lateinit var title_get:String
    lateinit var name_button:String
    var position:Int = 0
    var hour:Int = 0
    var minute:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page7_re)



        //intentを受け取る
        val dayOfmonth:String = intent.getStringExtra("Dayofmonth").toString()
        val month:String      = intent.getStringExtra("month").toString()
        val year:String       = intent.getStringExtra("year").toString()

        dead_day_page7.text = year+"/"+month+"/"+dayOfmonth
        name_button = intent.getStringExtra("name").toString()
        add_button.text = name_button


        //add_button.isEnabled = false


        if(name_button.equals("編集完了")) {
            position = intent.getIntExtra("position",0)
        }

        Kamoku_edit_button.setOnClickListener {
            val intent = Intent(this@page7_re,page11::class.java)
            startActivity(intent)
        }

//        dead_time_page7.addTextChangedListener(object: TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//                //処理
//                end_time = dead_time_page7.text.toString()
//                if(add_button.text.equals("")){
//                    add_button.isEnabled = false
//                }else {
//                    add_button.isEnabled = true
//                }
//            }

//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                //処理
//
//            }
//        })

        title_edit_page7.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //処理
                title_get = dead_day_page7.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //処理
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //処理
            }
        })

        this.initNumberhourPicker()
        this.initNumbertimePicker()

        add_button.setOnClickListener {
            if(name_button.equals("編集完了")) {
                add_task(hour,minute)
            }else {
                post_task(hour, minute)
            }
        }

        dead_day_page7.setOnClickListener {
            showDatePicker(dead_day_page7)
        }





    }

    private fun initNumbertimePicker(){
        Number_time.minValue = 0
        Number_time.maxValue = 59
        Number_time.value = 0
        Number_hour.setOnValueChangedListener { picker, oldVal, newVal ->
            hour = newVal
        }
    }

    private fun initNumberhourPicker(){
        Number_hour.minValue = 0
        Number_hour.maxValue = 23
        Number_hour.value = 0
        Number_hour.setOnValueChangedListener { picker, oldVal, newVal ->
            minute = newVal
        }

    }

    override fun onResume() {
        super.onResume()
        realm_page7 = Realm.getDefaultInstance()
        //抽出
        result = realm_page7.where(TaskDB::class.java).findAll().sort("task_uid")
        if(name_button.equals("編集完了")) {
            setText()
        }

    }


    override fun onPause() {
        super.onPause()
        realm_page7.close()
    }



    fun post_task(hour:Int,minute:Int){


            realm_page7.beginTransaction()  //開始処理
            val taskDB: TaskDB = realm_page7.createObject(TaskDB::class.java)

            taskDB.task_uid = "00000000"
            taskDB.task_title = title_edit_page7.text.toString()
            //taskDB.subject = Kamoku_edit_button.text.toString()
            taskDB.subject = "数学2"
            taskDB.dead_day = dead_day_page7.text.toString()
            taskDB.task_url = url_edit_page7.text.toString()
            taskDB.task_memo = memo_edit_page7.text.toString()
            taskDB.dead_hour = hour
            taskDB.dead_minute =  minute

            title_edit_page7.setText("")
            Kamoku_edit_button.text = ""
            url_edit_page7.setText("")
            memo_edit_page7.setText("")

            realm_page7.commitTransaction() //終了処理

    }










    fun showDatePicker(text_show: TextView){
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener() { view, year, month, dayOfMonth->
                text_show.setText("${year}/${month + 1}/${dayOfMonth}")
            },
            2021,
            4,
            1)
        datePickerDialog.show()
    }

    fun setText(){
        val r = result[position]
        realm_page7.beginTransaction()  //開始処理
        title_edit_page7.setText(r!!.task_title)
        Kamoku_edit_button.text = r!!.subject
        dead_day_page7.text = r!!.dead_day
        //Number_hour.setText(r!!.dead_time)
        url_edit_page7.setText(r!!.task_url)
        memo_edit_page7.setText(r.task_memo)
        realm_page7.commitTransaction()
    }

    fun add_task(minute:Int,hour:Int){
        val t = result[position]
        realm_page7.beginTransaction()  //開始処理
        t!!.task_title = title_edit_page7.text.toString()
        t!!.subject   =  Kamoku_edit_button.text.toString()
        t!!.dead_day = dead_day_page7.text.toString()
        t!!.dead_minute  = minute
        t!!.dead_hour    =  hour
        t!!.task_url   = url_edit_page7.text.toString()
        t!!.task_memo  = memo_edit_page7.text.toString()
        realm_page7.commitTransaction()
        finish()
    }
}