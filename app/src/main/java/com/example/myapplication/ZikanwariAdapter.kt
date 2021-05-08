package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomAdapter2(context: Context, var mAnimalList: List<ZikanwariDB>) : ArrayAdapter<ZikanwariDB>(context, 0, mAnimalList) {

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
        imageView?.setImageResource(animal.zikanwari_color)

        val name = view?.findViewById<TextView>(R.id.subject_listevent)
        name?.text = animal.zikanwari_title

        return view!!
    }
}