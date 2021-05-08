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
import kotlinx.android.synthetic.main.activity_page3_re.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import retrofit2.Retrofit

class Page3_re : AppCompatActivity() {

    private val retrofit = Retrofit.Builder().apply {
        baseUrl("https://hakadorikun.herokuapp.com/")
    }.build()

    //サービスクラスの実装オブジェクト取得
    private val service = retrofit.create(MyService::class.java)

    val get = service.getRawResponseForPosts()

    // 通信全体で利用するMediaType
    private val mediaType: MediaType = MediaType.parse("application/json; charset=utf-8")!!

    private val myViewModel: MyViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MyViewModel::class.java)
    }

    private val scope = CoroutineScope(Dispatchers.IO)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page3_re)

        //dpをpxに変換する関数
        fun dpToPx(dp: Int): Float {
            val metrics = getResources().getDisplayMetrics()
            return dp * metrics.density
        }
        //for文で複製
//        for(i in 1..40) {
//            val button: Button = Button(this)//buttonを定義
//            button.setBackgroundResource(R.drawable.group10)//バックグラウンドに画像をセット
//            toolbar9.setBackgroundResource(R.drawable.header)//ツールバーに画像をセット
//            val s1: String = getString(R.string.a)//string.xmlから文字列取得
//            val s2: String = getString(R.string.b)
//            val s3: String = getString(R.string.c)
//            val string = SpannableString("        " +s1 + "\n        " + s2 +"\n        " +s3)//いじれる文字列に変更
//            string.setSpan(RelativeSizeSpan(1.9f), 8, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)//文字列の一部の大きさを変える
//            string.setSpan(ForegroundColorSpan(Color.RED), 57, 74, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)//文字列の一部の色変更
//            button.gravity = Gravity.LEFT//gravity設定
//            button.text = string//text設定
//            button.setLineSpacing(7.0f,1.0f)//行間設定
//            linerlayout_parent.addView(button)//buttonを追加する
//            val dp = dpToPx(130).toInt()//dpをpxに変換
//            button.layoutParams.height = dp//高さを指定
//        }
    }


}