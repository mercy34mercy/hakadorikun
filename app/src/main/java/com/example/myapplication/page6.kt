package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import io.realm.Realm
import io.realm.RealmResults

import kotlinx.android.synthetic.main.activity_page6.*
import kotlinx.android.synthetic.main.activity_page8.*

class  page6 : AppCompatActivity() {
    var list_position:Int = 0
    var task_id:Int = 0
    lateinit var realm: Realm
    lateinit var result : RealmResults<TaskDB>
    var all_or_day:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page6)



        //all_or_day = intent.getStringArrayExtra("all").toString().toInt()
        list_position = intent.getIntExtra("position",0)
        task_id = intent.getIntExtra("id",0)




        button100.setOnClickListener {
            intent = Intent(this@page6,page7_re::class.java)
            intent.putExtra("name","編集完了")
            intent.putExtra("position",list_position)
            startActivity(intent)
        }

        //TODO convert_allを使う



        /*これは文字数を数える処理
        edittext.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //処理
                var text: String = edittext.text.toString()
                val textLength = text.length
                textView13.text = textLength.toString() + "文字" + koi
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //処理
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //処理
            }
        })
         */




    }


    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()
        //抽出
        result = realm.where(TaskDB::class.java).findAll().sort("task_number")

        val r = result[list_position]
        title_page6.text = r!!.task_title
        kyoka_page6.text = r!!.subject

        var minute:String = ""
        if(r!!.dead_minute < 10){
            minute = "0" + r!!.dead_minute.toString()
        }else{
            minute = r!!.dead_minute.toString()
        }

        dead_line_page6.text = r!!.dead_day + r!!.dead_hour.toString() + ":" + minute
        memo_edit_page6.text = r!!.task_memo
        url_page6.text = r!!.task_url

        if(r!!.task_condition == 1) {
            task_complete_button.text = "未提出にする"
        }

        task_complete_button.setOnClickListener {
            if(r!!.task_condition == 1){
                not_complete()
                finish()
            }else{
                task_complete()
                finish()
            }

        }

    }

    private fun not_complete() {
        realm.beginTransaction()  //開始処理
        val taskDB = result[list_position]
        taskDB!!.task_condition = 0
        realm.commitTransaction() //終了処理
    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }

    fun task_complete(){
        realm.beginTransaction()  //開始処理
        val taskDB = result[list_position]
        taskDB!!.task_condition = 1
        realm.commitTransaction() //終了処理
    }




}

