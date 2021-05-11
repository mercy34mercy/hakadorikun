package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class Only_TaskAdapter(context: Context, var mAnimalList: List<TaskDB>) : ArrayAdapter<TaskDB>(context, 0, mAnimalList) {

    private val layoutInflater: LayoutInflater
        get() = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Animalの取得
        val animal = mAnimalList[position]

        //val event= mAnimalList[position]!!.uid + mAnimalList[position]!!.title


        // レイアウトの設定
        var view = convertView
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.only_task_list, parent, false)
        }

        if (animal.task_condition == 1){
            val background = view?.findViewById<LinearLayout>(R.id.background_onlytask)
            background?.setBackgroundResource(R.color.gray)
        }
        var minute:String = ""

        if(animal.dead_minute < 10){
            minute = "0" + animal.dead_minute.toString()
        }else{
            minute = animal.dead_minute.toString()
        }

        // 各Viewの設定
        val imageView = view?.findViewById<ImageView>(R.id.image_onlytask)
        if (animal.task_condition == 1) {
            imageView?.setImageResource(R.drawable.iconhakadorikun)
        }
        val name = view?.findViewById<TextView>(R.id.subject_onlytask)
        name?.text = animal.subject

        val deadtime = view?.findViewById<TextView>(R.id.deadtime_onlytask)
        deadtime?.text = animal.dead_hour.toString() + ":" + minute

        val deadday = view?.findViewById<TextView>(R.id.dead_day_onlytask)
        deadday?.text = animal.dead_day







        val age = view?.findViewById<TextView>(R.id.title_listtask)
        //age?.text = "${animal.age} 才"
        age?.text = animal.task_title
        return view!!
    }
}