package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm

import io.realm.RealmResults

import kotlinx.android.synthetic.main.activity_home3.*
import kotlinx.android.synthetic.main.activity_page5_re.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class home3 : AppCompatActivity() {
    lateinit var realm: Realm
    lateinit var event_result : RealmResults<EveDB>
    lateinit var task_result: RealmResults<TaskDB>
    lateinit var subject_result: RealmResults<ZikanwariDB>
    lateinit var event_l:CustomAdapter
    lateinit var task_l:TaskAdapter
    lateinit var today:String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //データの初期化に使う
        setContentView(R.layout.activity_home3)
        val subject_id: Array<Button> = arrayOf(z_11,z_12,z_13,z_14,z_15,z_16,
            z_21,z_22,z_23,z_24,z_25,z_26,
            z_31,z_32,z_33,z_34,z_35,z_36,
            z_41,z_42,z_43,z_44,z_45,z_46,
            z_51,z_52,z_53,z_54,z_55,z_56)




        today = getNowDate().toString()

        calender_home3.setOnClickListener {
            intent = Intent(this@home3, page2::class.java)
            startActivity(intent)
        }

        task_home3.setOnClickListener {
            intent = Intent(this@home3, Page3_re::class.java)
            startActivity(intent)
        }

        setting_home3.setOnClickListener {
            intent = Intent(this@home3, home2::class.java)
            startActivity(intent)
        }
        for (i in 0..29) {
            subject_id[i].setOnClickListener {
                intent = Intent(this@home3, page11::class.java)
                intent.putExtra("position",i.toString())
                startActivity(intent)
            }
        }

//        for(i in 0..29){
//            subject_id[i].setBackgroundResource()
//        }


    }

    override fun onResume() {
        super.onResume()
        val subject_id: Array<Button> = arrayOf(z_11,z_12,z_13,z_14,z_15,z_16,
            z_21,z_22,z_23,z_24,z_25,z_26,
            z_31,z_32,z_33,z_34,z_35,z_36,
            z_41,z_42,z_43,z_44,z_45,z_46,
            z_51,z_52,z_53,z_54,z_55,z_56)

        realm = Realm.getDefaultInstance()

        event_result = realm.where(EveDB::class.java).equalTo("startday",today).findAll()
        task_result = realm.where(TaskDB::class.java).equalTo("dead_day",today).findAll()
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
        val color: Array<Int> = arrayOf( R.drawable.zikanwari_red,R.drawable.zikanwari_green, R.drawable.zikanwari_bulue, R.drawable.zikanwari_yellow, R.drawable.zikanwari_skybulue)
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