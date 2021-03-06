package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_page11.*
import kotlinx.android.synthetic.main.activity_page2.*
import kotlinx.android.synthetic.main.activity_page3_re.*
import kotlinx.android.synthetic.main.activity_page5_re.*
import java.util.ArrayList

class page5_re : AppCompatActivity() {
    lateinit var realm: Realm
    lateinit var realm2:Realm
    lateinit var text2: TextView
    var length: Int = 0
    lateinit var result :RealmResults<EveDB>
    lateinit var task_result:RealmResults<TaskDB>
    lateinit var event_l:CustomAdapter
    lateinit var task_l:TaskAdapter
    lateinit var year:String
    lateinit var dayOfmonth:String
    lateinit var month:String
    var clickcnt:Int = 0


//    var array_button:ArrayList<MaterialButton> = ArrayList<MaterialButton>()
    var array_button= ArrayList<MaterialButton>(100)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page5_re)
        var cout:Int = 0

        //intentを受け取る
        dayOfmonth = intent.getStringExtra("Dayofmonth").toString()
        month      = intent.getStringExtra("month").toString()
        year       = intent.getStringExtra("year").toString()

        val d:Int = dayOfmonth.toInt()
        val m:Int = month.toInt()
        val y:Int = year.toInt()

        dayOfmonth = d.toString()
        month = m.toString()
        year = y.toString()


        toolbar_page5_re.text = month + "月" + dayOfmonth + "日"

        //初期状態ではボタンは消しておく
        delete_button()

        List_Task.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@page5_re,page6::class.java)
            intent.putExtra("position",position)
            startActivity(intent)
        }

        List_Event.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@page5_re,page8::class.java)
            intent.putExtra("list_position",position)
            startActivity(intent)
        }

        //右下のプラスボタンその１
//        sub_btn1_page5.setOnClickListener {
//            val intent = Intent(this@page5_re, page7_re::class.java)
//            intent.putExtra("subject",edit_subject.text.toString())
//            intent.putExtra("Dayofmonth",dayOfmonth)
//            intent.putExtra("month",month)
//            intent.putExtra("year",year)
//            intent.putExtra("name","追加")
//            startActivity(intent)
//        }
//
//        //右下のプラスボタンその２
//        sub_btn2_page5.setOnClickListener {
//
//            val intent = Intent(this@page5_re, page9::class.java)
//            intent.putExtra("Dayofmonth",dayOfmonth)
//            intent.putExtra("month",month)
//            intent.putExtra("year",year)
//            intent.putExtra("name","追加")
//            startActivity(intent)
//        }

        floatingActionButton_page5.setOnClickListener {
            if(clickcnt%2 == 0) {
                create_button()
            }else{
                delete_button()
            }
            clickcnt++
        }

        event_page5.setOnClickListener {
            val intent = Intent(this@page5_re, page7_re::class.java)
            intent.putExtra("Dayofmonth",dayOfmonth)
            intent.putExtra("month",month)
            intent.putExtra("year",year)
            intent.putExtra("name","追加")
            delete_button()
            clickcnt++
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        task_page5.setOnClickListener {
            val intent = Intent(this@page5_re, page9::class.java)
            intent.putExtra("Dayofmonth",dayOfmonth)
            intent.putExtra("month",month)
            intent.putExtra("year",year)
            intent.putExtra("name","追加")
            delete_button()
            clickcnt++
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

/*
        for (i in 0..length-1) {
//            array_button[i]  = MaterialButton(this)

//            array_button[i].setBackgroundResource(R.drawable.group10)
//            array_button[i].setIconResource(R.drawable.green)
//            array_button[i].setBackgroundColor(849324)
//            array_button[i].setIconTintResource(R.color.green)
//            array_button[i].iconSize = Int.SIZE_BITS
//            array_button[i].gravity = Gravity.LEFT//gravity設定
////            button.text = event_data[i].toString()
//            liner_layout_page5.addView(array_button[i])
//            val dp = dpToPx(130).toInt()//dpをpxに変換
//            array_button[i].layoutParams.height = dp//高さを指定

            array_button[i].setOnClickListener{
                intent = Intent(this@page5_re,page6::class.java)
                //intent.putExtra("data",cout2)
                startActivity(intent)
            }

        }

 */




    }

    private fun delete_button()
    {
        task_page5.isClickable = false
        event_page5.isClickable = false
        task_page5.text = ""
        event_page5.text = ""
        task_page5.setBackgroundResource(R.color.clear)
        event_page5.setBackgroundResource(R.color.clear)
    }

    private fun create_button()
    {
        task_page5.isClickable = true
        event_page5.isClickable = true
        task_page5.text = "予定を追加"
        event_page5.text = "課題を追加"
        task_page5.setBackgroundResource(R.drawable.fillline)
        event_page5.setBackgroundResource(R.drawable.fillline)
    }


    override fun onResume() {
        super.onResume()

        realm = Realm.getDefaultInstance()
        realm2 = Realm.getDefaultInstance()

        //抽出
        //result = realm.where(EveDB::class.java).findAll().sort("startday","")

        val year_month_day:String =  year + "/" + month + "/"+ dayOfmonth
        result = realm.where(EveDB::class.java).equalTo("startday",year_month_day).findAll()
        task_result = realm2.where(TaskDB::class.java).equalTo("dead_day",year_month_day).lessThan("task_condition",1).findAll().sort("task_number")
        length = result.size


        event_l = CustomAdapter(this,result)
        task_l = TaskAdapter(this,task_result)
        List_Event.adapter = event_l
        List_Task.adapter = task_l







    }

    override fun onPause() {
        super.onPause()
        realm.close()
        realm2.close()
    }




}