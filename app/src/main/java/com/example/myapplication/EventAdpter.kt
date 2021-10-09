package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text
import java.time.LocalDate
import java.util.*

class CustomAdapter(context: Context, var mAnimalList: List<EveDB>,var date: Date) : ArrayAdapter<EveDB>(context, 0, mAnimalList) {

    private val layoutInflater: LayoutInflater
        get() = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Animalの取得
        val animal = mAnimalList[position]

        //val event= mAnimalList[position]!!.uid + mAnimalList[position]!!.title

        var stminute:String = ""

        if(animal.start_minute < 10){
            stminute = "0" + animal.start_minute.toString()
        }else{
            stminute = animal.start_minute.toString()
        }

        var edminute:String = ""

        if(animal.end_minute < 10){
            edminute = "0" + animal.end_minute.toString()
        }else{
            edminute = animal.end_minute.toString()
        }


//
//        val today:LocalDate = LocalDate.now()
//        val t = today.toString().split("-")
//        val java_today: Date = Date(t[0].toInt(),t[1].toInt(),t[2].toInt())



        // レイアウトの設定
        var view = convertView
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.list_event, parent, false)
        }


        //終日設定
        if(animal.alltime == 1){
            val sttime_ = view?.findViewById<TextView>(R.id.starttime_listevent)
            val edtime_ = view?.findViewById<TextView>(R.id.endtime_listevent)
            sttime_?.text = "終"
            edtime_?.text = "日"

        }else
        {
            val starttime = view?.findViewById<TextView>(R.id.starttime_listevent)
            starttime?.text = animal.start_hour.toString() + ":" + stminute

            val endtime = view?.findViewById<TextView>(R.id.endtime_listevent)
            endtime?.text = animal.end_hour.toString() + ":" + edminute
        }

        if(date.after(animal.javastdate)) {
            val starttime = view?.findViewById<TextView>(R.id.starttime_listevent)
            starttime?.text = "0:00"
        }
        if(date.before(animal.javaeddate)){
            val endtime = view?.findViewById<TextView>(R.id.endtime_listevent)
            endtime?.text = "0:00"
        }
        if(date.after(animal.javastdate) && date.before(animal.javaeddate)){
            val sttime_ = view?.findViewById<TextView>(R.id.starttime_listevent)
            val edtime_ = view?.findViewById<TextView>(R.id.endtime_listevent)
            sttime_?.text = "終"
            edtime_?.text = "日"

        }

        // 各Viewの設定
        val imageView = view?.findViewById<ImageView>(R.id.image)
        imageView?.setImageResource(animal.iconstyle)

        val place = view?.findViewById<TextView>(R.id.subject_listevent)
        place?.text = animal.place

        val title = view?.findViewById<TextView>(R.id.title_list)
        title?.text = animal.title




        return view!!
    }
}