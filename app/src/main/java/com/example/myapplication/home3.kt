package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm

import io.realm.RealmResults

import kotlinx.android.synthetic.main.activity_home3.*
import kotlinx.android.synthetic.main.activity_page5_re.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class home3 : AppCompatActivity() {
    lateinit var realm: Realm
    lateinit var event_result : RealmResults<EveDB>
    lateinit var task_result: RealmResults<TaskDB>
    lateinit var subject_result: RealmResults<ZikanwariDB>
    lateinit var event_l:CustomAdapter
    lateinit var task_l:TaskAdapter
    lateinit var today:String
    //課題達成率を求めるために課題の終わってるやつの要素数を取得する
    lateinit var task_result_done: RealmResults<TaskDB>




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //データの初期化に使う
        setContentView(R.layout.activity_home3)
        val subject_id: Array<Button> = arrayOf(z_11,z_12,z_13,z_14,z_15,z_16,
            z_21,z_22,z_23,z_24,z_25,z_26,
            z_31,z_32,z_33,z_34,z_35,z_36,
            z_41,z_42,z_43,z_44,z_45,z_46,
            z_51,z_52,z_53,z_54,z_55,z_56)

        val onlyDate: LocalDate = LocalDate.now()
        val s:String = onlyDate.toString()
        val day = s.split("-")

        val m:String  = day[1].toInt().toString()
        val d:String  = day[2].toInt().toString()



        today = day[0] + "/" + m + "/" + d




        calender_home3.setOnClickListener {
            intent = Intent(this@home3, page2::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        task_home3.setOnClickListener {
            intent = Intent(this@home3, Page3_re::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        setting_home3.setOnClickListener {
            intent = Intent(this@home3, home2::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        for (i in 0..29) {
            subject_id[i].setOnClickListener {
                intent = Intent(this@home3, page11::class.java)
                intent.putExtra("position", i.toString())
                startActivity(intent)

            }
        }

        home_task.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@home3, page6::class.java)
            intent.putExtra("name","編集完了")
            intent.putExtra("Dayofmonth",d)
            intent.putExtra("month",m)
            intent.putExtra("year",day[0])
            intent.putExtra("position", position)
            startActivity(intent)

        }


        home_event.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@home3, page8::class.java)
            intent.putExtra("name","編集完了")
            intent.putExtra("Dayofmonth",d)
            intent.putExtra("month",m)
            intent.putExtra("year",day[0])
            intent.putExtra("list_position", position)
            startActivity(intent)

        }

    }


    override fun onResume() {
        super.onResume()
        val subject_id: Array<Button> = arrayOf(z_11,z_12,z_13,z_14,z_15,z_16,
            z_21,z_22,z_23,z_24,z_25,z_26,
            z_31,z_32,z_33,z_34,z_35,z_36,
            z_41,z_42,z_43,z_44,z_45,z_46,
            z_51,z_52,z_53,z_54,z_55,z_56)

        realm = Realm.getDefaultInstance()

        //終わってる課題の要素数を調べる
        task_result_done = realm.where(TaskDB::class.java).equalTo("dead_day",today).greaterThanOrEqualTo("task_condition",1).findAll()
        val done_task:Int = task_result_done.size

        task_result = realm.where(TaskDB::class.java).equalTo("dead_day",today).findAll().sort("task_number")
        val all_tasl:Double = task_result.size.toDouble()

        val tasseiritu:Int = ((done_task/all_tasl)*100).toInt()
        tasseiritu_home3.text = tasseiritu.toString()

        event_result = realm.where(EveDB::class.java).equalTo("startday",today).findAll()
        subject_result = realm.where(ZikanwariDB::class.java).findAll()

        val task_size:Int = task_result.size
        val event_size:Int = event_result.size
        val subject_size:Int = subject_result.size

        event_l = CustomAdapter(this,event_result)
        task_l = TaskAdapter(this,task_result)
        home_task.adapter = task_l
        home_event.adapter = event_l

        val a = dpTopx(100,this)

        home_event.layoutParams.height = (a * event_size).toInt()//高さを指定
        home_task.layoutParams.height = (a * task_size).toInt()//高さを指定
        if(subject_result.isEmpty()) {

        }else{
            set_subject(subject_size)
        }

    }

    private fun set_subject(size:Int) {
        val color: Array<Int> = arrayOf( R.drawable.zikanwari_green,R.drawable.zikanwari_red, R.drawable.zikanwari_yellow, R.drawable.zikanwari_bulue, R.drawable.zikanwari_skybulue)
        val sub_id: Array<Button> = arrayOf(z_11,z_12,z_13,z_14,z_15,z_16,
            z_21,z_22,z_23,z_24,z_25,z_26,
            z_31,z_32,z_33,z_34,z_35,z_36,
            z_41,z_42,z_43,z_44,z_45,z_46,
            z_51,z_52,z_53,z_54,z_55,z_56)
        for(l in 0..29){
            val button:Button = sub_id[l]
            button.setBackgroundResource(R.drawable.zikan_none)
            button.text = ""
        }

        for(i in 0..size-1){
            val button:Button = sub_id[subject_result[i]!!.kyoka_zigen]
            button.setBackgroundResource(color[subject_result[i]!!.zikanwari_color_i])
            button.text = subject_result[i]!!.zikanwari_title
        }
    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }

    fun getNowDate(): String? {
        val df: DateFormat = SimpleDateFormat("yyyy/MM/dd")
        val date = Date(System.currentTimeMillis())
        return df.format(date)
    }

    fun dpTopx(dp: Int, context: Context):Float {
        val metrics = context.getResources().getDisplayMetrics()
        return dp * metrics.density
    }
}