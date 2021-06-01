package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class TaskAdapter(context: Context, var mAnimalList: List<TaskDB>) : ArrayAdapter<TaskDB>(context, 0, mAnimalList) {

    private val layoutInflater: LayoutInflater
        get() = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Animalの取得
        val animal = mAnimalList[position]

        //val event= mAnimalList[position]!!.uid + mAnimalList[position]!!.title


        // レイアウトの設定
        var view = convertView
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.list_task, parent, false)
        }

        if (animal.task_condition == 1){
            //view!!.layoutParams.height = 0
            val background = view?.findViewById<LinearLayout>(R.id.background)
            background?.setBackgroundResource(R.color.gray)
            val stamp = view?.findViewById<ImageView>(R.id.stamp_box)
            stamp?.setImageResource(R.drawable.iconhakadorikun)
        }
        var minute:String = ""

        if(animal.dead_minute < 10){
            minute = "0" + animal.dead_minute.toString()
        }else{
            minute = animal.dead_minute.toString()
        }

        // 各Viewの設定
        val imageView = view?.findViewById<ImageView>(R.id.image_task)

        val name = view?.findViewById<TextView>(R.id.subject_listtask)
        name?.text = animal.subject

        val deadtime = view?.findViewById<TextView>(R.id.deadtime_listtask)
        deadtime?.text = animal.dead_hour.toString() + ":" + minute







        val age = view?.findViewById<TextView>(R.id.title_listtask)
        //age?.text = "${animal.age} 才"
        age?.text = animal.task_title
        return view!!
    }
}