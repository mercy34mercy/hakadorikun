package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import io.realm.Realm
import io.realm.RealmResults
import java.time.LocalDateTime

class TaskAdapter(context: Context, var mAnimalList: List<TaskDB>) : ArrayAdapter<TaskDB>(context, 0, mAnimalList) {

    lateinit var realm_taskadapter :Realm
    lateinit var subjct_result: RealmResults<ZikanwariDB>
    val color: Array<Int> = arrayOf(R.drawable.green_line, R.drawable.red_line, R.drawable.yellow_line, R.drawable.darkbulue_line, R.drawable.skybulue_line)

    private val layoutInflater: LayoutInflater
        get() = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Animalの取得
        val animal = mAnimalList[position]
        val subject_s:String = animal.subject
        realm_taskadapter = Realm.getDefaultInstance()
        subjct_result = realm_taskadapter.where(ZikanwariDB::class.java).equalTo("zikanwari_title",subject_s).findAll().sort("zikanwari_color")
        var color_i = 0
        if(subjct_result.isEmpty()) {
        }else {

            color_i = subjct_result[0]!!.zikanwari_color_i
        }

        val subject_color_i:Int = color[color_i]


        //val event= mAnimalList[position]!!.uid + mAnimalList[position]!!.title


        // レイアウトの設定
        var view = convertView
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.list_task, parent, false)
        }

        val dead_day = generate(animal.dead_day,animal.dead_hour,animal.dead_minute)
        //期限切れの設定
        if(dead_day.isBefore(LocalDateTime.now())) {
            val background = view?.findViewById<LinearLayout>(R.id.background_list)
            background?.setBackgroundResource(R.color.red)


        }

        //課題完了時の効果
        if (animal.task_condition == 1) {
            //view!!.layoutParams.height = 0
            val background = view?.findViewById<LinearLayout>(R.id.background_list)
            background?.setBackgroundResource(R.color.gray)
            val stamp = view?.findViewById<ImageView>(R.id.stamp_box)
            stamp?.setImageResource(R.drawable.iconhakadorikun)
        }


        var minute: String = ""

        minute = if (animal.dead_minute < 10) {
            "0" + animal.dead_minute.toString()
        } else {
            animal.dead_minute.toString()
        }

        // 各Viewの設定
        val imageView = view?.findViewById<ImageView>(R.id.image_task)
        imageView?.setImageResource(subject_color_i)

        val name = view?.findViewById<TextView>(R.id.subject_listtask)
        name?.text = animal.subject
        //期限切れの設定

        if(dead_day.isBefore(LocalDateTime.now())) {
            val deadtime = view?.findViewById<TextView>(R.id.deadtime_listtask)
            deadtime?.text = "期限切れ"


        }else {
            val deadtime = view?.findViewById<TextView>(R.id.deadtime_listtask)
            deadtime?.text = animal.dead_hour.toString() + ":" + minute
        }

        val age = view?.findViewById<TextView>(R.id.title_listtask)
        //age?.text = "${animal.age} 才"
        age?.text = animal.task_title
        return view!!
    }
}