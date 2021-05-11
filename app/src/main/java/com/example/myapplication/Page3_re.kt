package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.scaleMatrix
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_page3_re.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import retrofit2.Retrofit

class Page3_re : AppCompatActivity() {
    lateinit var realm:Realm
    lateinit var result:RealmResults<TaskDB>
    lateinit var task:Only_TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page3_re)




   }

    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()
        result = realm.where(TaskDB::class.java)
                .sort("task_year").sort("task_month")
                .sort("task_day").sort("dead_hour")
                .sort("dead_minute").findAll()

        task = Only_TaskAdapter(this,result)
        task_list.adapter = task


    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }


}