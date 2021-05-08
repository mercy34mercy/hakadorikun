package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class CustomAdapter(context: Context, var mAnimalList: List<EveDB>) : ArrayAdapter<EveDB>(context, 0, mAnimalList) {

    private val layoutInflater: LayoutInflater
        get() = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Animalの取得
        val animal = mAnimalList[position]

        //val event= mAnimalList[position]!!.uid + mAnimalList[position]!!.title


        // レイアウトの設定
        var view = convertView
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.list_event, parent, false)
        }

        // 各Viewの設定
        val imageView = view?.findViewById<ImageView>(R.id.image)
        imageView?.setImageResource(animal.iconstyle)

        val place = view?.findViewById<TextView>(R.id.subject_listevent)
        place?.text = animal.place

        val title = view?.findViewById<TextView>(R.id.title_list)
        title?.text = animal.title


        val starttime = view?.findViewById<TextView>(R.id.starttime_listevent)
        starttime?.text = animal.start_hour.toString() + ":" + animal.start_minute

        val endtime = view?.findViewById<TextView>(R.id.endtime_listevent)
        endtime?.text = animal.end_hour.toString() + ":" + animal.end_minute.toString()

        return view!!
    }
}