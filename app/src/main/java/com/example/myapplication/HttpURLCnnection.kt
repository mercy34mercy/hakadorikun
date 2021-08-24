package com.example.myapplication

import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class Download()  {
val CONNECTION_TIMEOUT_MILLISECONDS = 6000
val READ_TIMEOUT_MILLISECONDS = 6000

   fun get(get_url:String,textbox:TextView) {
       GlobalScope.launch {
           // HttpURLConnectionの作成
           val url = URL(get_url)
           val connection = url.openConnection() as HttpURLConnection
           try {
               // ミリ秒単位でタイムアウトを設定
               connection.connectTimeout = CONNECTION_TIMEOUT_MILLISECONDS
               connection.readTimeout = READ_TIMEOUT_MILLISECONDS

//        connection.requestMethod = "GET"
//        connection.connect()

               // Responseの読み出し
               val statusCode = connection.responseCode
               if (statusCode == HttpURLConnection.HTTP_OK) {
                   readStream(connection.inputStream,textbox)
               }
           } catch (exception: Exception) {
               Log.e("Error", exception.toString())
           } finally {
               connection.disconnect()
           }
       }
   }


    fun readStream(inputStream: InputStream,textbox:TextView) {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val responseBody = bufferedReader.use { it.readText() }
        bufferedReader.close()

        Log.d("レスポンスデータ : ", responseBody)
        textbox.text = responseBody
    }
}