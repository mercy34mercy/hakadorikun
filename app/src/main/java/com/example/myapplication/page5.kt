package com.example.myapplication

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_page5.*
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class page5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page5)

        val bundle = intent.extras
        //date_page5　の中身　2021/2/10
        val date_page5: String? = bundle?.getString("date_page2")
        val dateful_page5: String? = bundle?.getString("dateful_page5")
        toolbar.title = dateful_page5



        val URL = "https://beginners-hack-demo2.herokuapp.com/DL_sampledata/" + date_page5

        val URL_1:String = URL +"/a"+"/EVENT"
        val URL_2:String = URL +"/b"+"/EVENT"
        val URL_3:String = URL +"/c"+"/EVENT"
        val URL_4:String = URL +"/d"+"/EVENT"
        val URL_5:String = URL +"/e"+"/EVENT"

        val URL_6:String = URL +"/a"+"/TASK"
        val URL_7:String = URL +"/b"+"/TASK"
        val URL_8:String = URL +"/c"+"/TASK"
        val URL_9:String = URL +"/d"+"/TASK"
        val URL_10:String = URL +"/e"+"/TASK"



                        //ボタンがクリックされたらAPIを叩く。
        HitAPITask().execute("" + URL_1)
        HitAPITask2().execute(""+URL_2)
        HitAPITask3().execute(""+URL_3)
        HitAPITask4().execute(""+URL_4)
        HitAPITask5().execute(""+URL_5)
        HitAPITask6().execute(""+URL_6)
        HitAPITask7().execute(""+URL_7)
        HitAPITask8().execute(""+URL_8)
        HitAPITask9().execute(""+URL_9)
        HitAPITask10().execute(""+URL_10)




        event_1.setOnClickListener{
            intent = Intent(this@page5,page9::class.java)
            intent.putExtra("date", URL_1)
            startActivity(intent)

        }
        event_2.setOnClickListener{
            intent = Intent(this@page5,page9::class.java)
            intent.putExtra("date", URL_2)
            startActivity(intent)

        }
        event_3.setOnClickListener{
            intent = Intent(this@page5,page9::class.java)
            intent.putExtra("date", URL_3)
            startActivity(intent)
        }
        event_4.setOnClickListener{
            intent = Intent(this@page5,page9::class.java)
            intent.putExtra("date", URL_4)
            startActivity(intent)
        }
//        event_5.setOnClickListener{
//            intent = Intent(this@page5,page8::class.java)
//            intent.putExtra("date", URL_5)
//            startActivity(intent)
//        }


        plan_1.setOnClickListener{
            intent = Intent(this@page5,page6::class.java)
            intent.putExtra("date", URL_6)
            startActivity(intent)
        }
        plan_2.setOnClickListener{
            intent = Intent(this@page5,page6::class.java)
            intent.putExtra("date", URL_7)
            startActivity(intent)
        }
        plan_3.setOnClickListener{
            intent = Intent(this@page5,page6::class.java)
            intent.putExtra("date", URL_8)
            startActivity(intent)
        }
        plan_4.setOnClickListener{
            intent = Intent(this@page5,page6::class.java)
            intent.putExtra("date", URL_10)
            startActivity(intent)
        }









            }

            inner class HitAPITask : AsyncTask<String, String, String>() {

                override fun doInBackground(vararg params: String?): String? {
                    //ここでAPIを叩きます。バックグラウンドで処理する内容です。
                    var connection: HttpURLConnection? = null
                    var reader: BufferedReader? = null
                    val buffer: StringBuffer

                    try {
                        //param[0]にはAPIのURI(String)を入れます(後ほど)。
                        //AsynkTask<...>の一つめがStringな理由はURIをStringで指定するからです。
                        val url = URL(params[0])
                        connection = url.openConnection() as HttpURLConnection
                        connection.connect()  //ここで指定したAPIを叩いてみてます。

                        //ここから叩いたAPIから帰ってきたデータを使えるよう処理していきます。

                        //とりあえず取得した文字をbufferに。
                        val stream = connection.inputStream
                        reader = BufferedReader(InputStreamReader(stream))
                        buffer = StringBuffer()
                        var line: String?
                        while (true) {
                            line = reader.readLine()
                            if (line == null) {
                                break
                            }
                            buffer.append(line)
                            //Log.d("CHECK", buffer.toString())
                        }

                        //ここからは、今回はJSONなので、いわゆるJsonをParseする作業（Jsonの中の一つ一つのデータを取るような感じ）をしていきます。

                        //先ほどbufferに入れた、取得した文字列
                        val jsonText = buffer.toString()

                        //JSONObjectを使って、まず全体のJSONObjectを取ります。
                        val parentJsonObj = JSONObject(jsonText)
                        //今回のJSONは配列になっているので（データは一つですが）、全体のJSONObjectから、getJSONArrayで配列"movies"を取ります。
                        val parentJsonArray = parentJsonObj.getJSONArray("data")

                        //JSONArrayの中身を取ります。映画"Your Name"のデータは、配列"movies"の０番目のデータなので、
                        val detailJsonObj = parentJsonArray.getJSONObject(0)  //これもJSONObjectとして取得

                        //moviesの0番目のデータのtitle項目をStringで取ります。これで中身を取れました。
                        val movieName: String = detailJsonObj.getString("TITLE")  // => Your Name.

                        //Stringでreturnしてあげましょう。
                        return "$movieName"

                        //ここから下は、接続エラーとかJSONのエラーとかで失敗した時にエラーを処理する為のものです。
                    } catch (e: MalformedURLException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    //finallyで接続を切断してあげましょう。
                    finally {
                        connection?.disconnect()
                        try {
                            reader?.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                    //失敗した時はnullやエラーコードなどを返しましょう。
                    return null
                }


                //返ってきたデータをビューに反映させる処理はonPostExecuteに書きます。これはメインスレッドです。
                override fun onPostExecute(result: String?) {
                    super.onPostExecute(result)
                    if (result == null) return

                    event_1.text = result
                }
            }

    inner class HitAPITask2 : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String?): String? {
            //ここでAPIを叩きます。バックグラウンドで処理する内容です。
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            val buffer: StringBuffer

            try {
                //param[0]にはAPIのURI(String)を入れます(後ほど)。
                //AsynkTask<...>の一つめがStringな理由はURIをStringで指定するからです。
                val url = URL(params[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()  //ここで指定したAPIを叩いてみてます。

                //ここから叩いたAPIから帰ってきたデータを使えるよう処理していきます。

                //とりあえず取得した文字をbufferに。
                val stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                buffer = StringBuffer()
                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    buffer.append(line)
                    //Log.d("CHECK", buffer.toString())
                }

                //ここからは、今回はJSONなので、いわゆるJsonをParseする作業（Jsonの中の一つ一つのデータを取るような感じ）をしていきます。

                //先ほどbufferに入れた、取得した文字列
                val jsonText = buffer.toString()

                //JSONObjectを使って、まず全体のJSONObjectを取ります。
                val parentJsonObj = JSONObject(jsonText)
                //今回のJSONは配列になっているので（データは一つですが）、全体のJSONObjectから、getJSONArrayで配列"movies"を取ります。
                val parentJsonArray = parentJsonObj.getJSONArray("data")

                //JSONArrayの中身を取ります。映画"Your Name"のデータは、配列"movies"の０番目のデータなので、
                val detailJsonObj = parentJsonArray.getJSONObject(0)  //これもJSONObjectとして取得

                //moviesの0番目のデータのtitle項目をStringで取ります。これで中身を取れました。
                val movieName: String = detailJsonObj.getString("TITLE")  // => Your Name.

                //Stringでreturnしてあげましょう。
                return "$movieName"  // => Your Name. - 2016

                //ここから下は、接続エラーとかJSONのエラーとかで失敗した時にエラーを処理する為のものです。
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            //finallyで接続を切断してあげましょう。
            finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            //失敗した時はnullやエラーコードなどを返しましょう。
            return null
        }


        //返ってきたデータをビューに反映させる処理はonPostExecuteに書きます。これはメインスレッドです。
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result == null) return

            event_2.text = result
        }
    }

    inner class HitAPITask3 : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String?): String? {
            //ここでAPIを叩きます。バックグラウンドで処理する内容です。
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            val buffer: StringBuffer

            try {
                //param[0]にはAPIのURI(String)を入れます(後ほど)。
                //AsynkTask<...>の一つめがStringな理由はURIをStringで指定するからです。
                val url = URL(params[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()  //ここで指定したAPIを叩いてみてます。

                //ここから叩いたAPIから帰ってきたデータを使えるよう処理していきます。

                //とりあえず取得した文字をbufferに。
                val stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                buffer = StringBuffer()
                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    buffer.append(line)
                    //Log.d("CHECK", buffer.toString())
                }

                //ここからは、今回はJSONなので、いわゆるJsonをParseする作業（Jsonの中の一つ一つのデータを取るような感じ）をしていきます。

                //先ほどbufferに入れた、取得した文字列
                val jsonText = buffer.toString()

                //JSONObjectを使って、まず全体のJSONObjectを取ります。
                val parentJsonObj = JSONObject(jsonText)
                //今回のJSONは配列になっているので（データは一つですが）、全体のJSONObjectから、getJSONArrayで配列"movies"を取ります。
                val parentJsonArray = parentJsonObj.getJSONArray("data")

                //JSONArrayの中身を取ります。映画"Your Name"のデータは、配列"movies"の０番目のデータなので、
                val detailJsonObj = parentJsonArray.getJSONObject(0)  //これもJSONObjectとして取得

                //moviesの0番目のデータのtitle項目をStringで取ります。これで中身を取れました。
                val movieName: String = detailJsonObj.getString("TITLE")  // => Your Name.

                //Stringでreturnしてあげましょう。
                return "$movieName"  // => Your Name. - 2016

                //ここから下は、接続エラーとかJSONのエラーとかで失敗した時にエラーを処理する為のものです。
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            //finallyで接続を切断してあげましょう。
            finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            //失敗した時はnullやエラーコードなどを返しましょう。
            return null
        }


        //返ってきたデータをビューに反映させる処理はonPostExecuteに書きます。これはメインスレッドです。
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result == null) return

            event_3.text = result
        }
    }

    inner class HitAPITask4 : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String?): String? {
            //ここでAPIを叩きます。バックグラウンドで処理する内容です。
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            val buffer: StringBuffer

            try {
                //param[0]にはAPIのURI(String)を入れます(後ほど)。
                //AsynkTask<...>の一つめがStringな理由はURIをStringで指定するからです。
                val url = URL(params[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()  //ここで指定したAPIを叩いてみてます。

                //ここから叩いたAPIから帰ってきたデータを使えるよう処理していきます。

                //とりあえず取得した文字をbufferに。
                val stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                buffer = StringBuffer()
                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    buffer.append(line)
                    //Log.d("CHECK", buffer.toString())
                }

                //ここからは、今回はJSONなので、いわゆるJsonをParseする作業（Jsonの中の一つ一つのデータを取るような感じ）をしていきます。

                //先ほどbufferに入れた、取得した文字列
                val jsonText = buffer.toString()

                //JSONObjectを使って、まず全体のJSONObjectを取ります。
                val parentJsonObj = JSONObject(jsonText)
                //今回のJSONは配列になっているので（データは一つですが）、全体のJSONObjectから、getJSONArrayで配列"movies"を取ります。
                val parentJsonArray = parentJsonObj.getJSONArray("data")

                //JSONArrayの中身を取ります。映画"Your Name"のデータは、配列"movies"の０番目のデータなので、
                val detailJsonObj = parentJsonArray.getJSONObject(0)  //これもJSONObjectとして取得

                //moviesの0番目のデータのtitle項目をStringで取ります。これで中身を取れました。
                val movieName: String = detailJsonObj.getString("TITLE")  // => Your Name.

                //Stringでreturnしてあげましょう。
                return "$movieName"  // => Your Name. - 2016

                //ここから下は、接続エラーとかJSONのエラーとかで失敗した時にエラーを処理する為のものです。
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            //finallyで接続を切断してあげましょう。
            finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            //失敗した時はnullやエラーコードなどを返しましょう。
            return null
        }


        //返ってきたデータをビューに反映させる処理はonPostExecuteに書きます。これはメインスレッドです。
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result == null) return

            event_4.text = result
        }
    }

    inner class HitAPITask5 : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String?): String? {
            //ここでAPIを叩きます。バックグラウンドで処理する内容です。
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            val buffer: StringBuffer

            try {
                //param[0]にはAPIのURI(String)を入れます(後ほど)。
                //AsynkTask<...>の一つめがStringな理由はURIをStringで指定するからです。
                val url = URL(params[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()  //ここで指定したAPIを叩いてみてます。

                //ここから叩いたAPIから帰ってきたデータを使えるよう処理していきます。

                //とりあえず取得した文字をbufferに。
                val stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                buffer = StringBuffer()
                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    buffer.append(line)
                    //Log.d("CHECK", buffer.toString())
                }

                //ここからは、今回はJSONなので、いわゆるJsonをParseする作業（Jsonの中の一つ一つのデータを取るような感じ）をしていきます。

                //先ほどbufferに入れた、取得した文字列
                val jsonText = buffer.toString()

                //JSONObjectを使って、まず全体のJSONObjectを取ります。
                val parentJsonObj = JSONObject(jsonText)
                //今回のJSONは配列になっているので（データは一つですが）、全体のJSONObjectから、getJSONArrayで配列"movies"を取ります。
                val parentJsonArray = parentJsonObj.getJSONArray("data")

                //JSONArrayの中身を取ります。映画"Your Name"のデータは、配列"movies"の０番目のデータなので、
                val detailJsonObj = parentJsonArray.getJSONObject(0)  //これもJSONObjectとして取得

                //moviesの0番目のデータのtitle項目をStringで取ります。これで中身を取れました。
                val movieName: String = detailJsonObj.getString("TITLE")  // => Your Name.

                //Stringでreturnしてあげましょう。
                return "$movieName"  // => Your Name. - 2016

                //ここから下は、接続エラーとかJSONのエラーとかで失敗した時にエラーを処理する為のものです。
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            //finallyで接続を切断してあげましょう。
            finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            //失敗した時はnullやエラーコードなどを返しましょう。
            return null
        }


        //返ってきたデータをビューに反映させる処理はonPostExecuteに書きます。これはメインスレッドです。
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result == null) return

//            event_5.text = result
        }
    }









    inner class HitAPITask6 : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String?): String? {
            //ここでAPIを叩きます。バックグラウンドで処理する内容です。
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            val buffer: StringBuffer

            try {
                //param[0]にはAPIのURI(String)を入れます(後ほど)。
                //AsynkTask<...>の一つめがStringな理由はURIをStringで指定するからです。
                val url = URL(params[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()  //ここで指定したAPIを叩いてみてます。

                //ここから叩いたAPIから帰ってきたデータを使えるよう処理していきます。

                //とりあえず取得した文字をbufferに。
                val stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                buffer = StringBuffer()
                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    buffer.append(line)
                    //Log.d("CHECK", buffer.toString())
                }

                //ここからは、今回はJSONなので、いわゆるJsonをParseする作業（Jsonの中の一つ一つのデータを取るような感じ）をしていきます。

                //先ほどbufferに入れた、取得した文字列
                val jsonText = buffer.toString()

                //JSONObjectを使って、まず全体のJSONObjectを取ります。
                val parentJsonObj = JSONObject(jsonText)
                //今回のJSONは配列になっているので（データは一つですが）、全体のJSONObjectから、getJSONArrayで配列"movies"を取ります。
                val parentJsonArray = parentJsonObj.getJSONArray("data")

                //JSONArrayの中身を取ります。映画"Your Name"のデータは、配列"movies"の０番目のデータなので、
                val detailJsonObj = parentJsonArray.getJSONObject(0)  //これもJSONObjectとして取得

                //moviesの0番目のデータのtitle項目をStringで取ります。これで中身を取れました。
                val movieName: String = detailJsonObj.getString("TITLE")  // => Your Name.

                //Stringでreturnしてあげましょう。
                return "$movieName"

                //ここから下は、接続エラーとかJSONのエラーとかで失敗した時にエラーを処理する為のものです。
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            //finallyで接続を切断してあげましょう。
            finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            //失敗した時はnullやエラーコードなどを返しましょう。
            return null
        }


        //返ってきたデータをビューに反映させる処理はonPostExecuteに書きます。これはメインスレッドです。
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result == null) return

            plan_1.text = result
        }
    }

    inner class HitAPITask7 : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String?): String? {
            //ここでAPIを叩きます。バックグラウンドで処理する内容です。
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            val buffer: StringBuffer

            try {
                //param[0]にはAPIのURI(String)を入れます(後ほど)。
                //AsynkTask<...>の一つめがStringな理由はURIをStringで指定するからです。
                val url = URL(params[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()  //ここで指定したAPIを叩いてみてます。

                //ここから叩いたAPIから帰ってきたデータを使えるよう処理していきます。

                //とりあえず取得した文字をbufferに。
                val stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                buffer = StringBuffer()
                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    buffer.append(line)
                    //Log.d("CHECK", buffer.toString())
                }

                //ここからは、今回はJSONなので、いわゆるJsonをParseする作業（Jsonの中の一つ一つのデータを取るような感じ）をしていきます。

                //先ほどbufferに入れた、取得した文字列
                val jsonText = buffer.toString()

                //JSONObjectを使って、まず全体のJSONObjectを取ります。
                val parentJsonObj = JSONObject(jsonText)
                //今回のJSONは配列になっているので（データは一つですが）、全体のJSONObjectから、getJSONArrayで配列"movies"を取ります。
                val parentJsonArray = parentJsonObj.getJSONArray("data")

                //JSONArrayの中身を取ります。映画"Your Name"のデータは、配列"movies"の０番目のデータなので、
                val detailJsonObj = parentJsonArray.getJSONObject(0)  //これもJSONObjectとして取得

                //moviesの0番目のデータのtitle項目をStringで取ります。これで中身を取れました。
                val movieName: String = detailJsonObj.getString("TITLE")  // => Your Name.

                //Stringでreturnしてあげましょう。
                return "$movieName"  // => Your Name. - 2016

                //ここから下は、接続エラーとかJSONのエラーとかで失敗した時にエラーを処理する為のものです。
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            //finallyで接続を切断してあげましょう。
            finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            //失敗した時はnullやエラーコードなどを返しましょう。
            return null
        }


        //返ってきたデータをビューに反映させる処理はonPostExecuteに書きます。これはメインスレッドです。
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result == null) return

            plan_2.text = result
        }
    }

    inner class HitAPITask8 : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String?): String? {
            //ここでAPIを叩きます。バックグラウンドで処理する内容です。
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            val buffer: StringBuffer

            try {
                //param[0]にはAPIのURI(String)を入れます(後ほど)。
                //AsynkTask<...>の一つめがStringな理由はURIをStringで指定するからです。
                val url = URL(params[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()  //ここで指定したAPIを叩いてみてます。

                //ここから叩いたAPIから帰ってきたデータを使えるよう処理していきます。

                //とりあえず取得した文字をbufferに。
                val stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                buffer = StringBuffer()
                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    buffer.append(line)
                    //Log.d("CHECK", buffer.toString())
                }

                //ここからは、今回はJSONなので、いわゆるJsonをParseする作業（Jsonの中の一つ一つのデータを取るような感じ）をしていきます。

                //先ほどbufferに入れた、取得した文字列
                val jsonText = buffer.toString()

                //JSONObjectを使って、まず全体のJSONObjectを取ります。
                val parentJsonObj = JSONObject(jsonText)
                //今回のJSONは配列になっているので（データは一つですが）、全体のJSONObjectから、getJSONArrayで配列"movies"を取ります。
                val parentJsonArray = parentJsonObj.getJSONArray("data")

                //JSONArrayの中身を取ります。映画"Your Name"のデータは、配列"movies"の０番目のデータなので、
                val detailJsonObj = parentJsonArray.getJSONObject(0)  //これもJSONObjectとして取得

                //moviesの0番目のデータのtitle項目をStringで取ります。これで中身を取れました。
                val movieName: String = detailJsonObj.getString("TITLE")  // => Your Name.

                //Stringでreturnしてあげましょう。
                return "$movieName"  // => Your Name. - 2016

                //ここから下は、接続エラーとかJSONのエラーとかで失敗した時にエラーを処理する為のものです。
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            //finallyで接続を切断してあげましょう。
            finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            //失敗した時はnullやエラーコードなどを返しましょう。
            return null
        }


        //返ってきたデータをビューに反映させる処理はonPostExecuteに書きます。これはメインスレッドです。
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result == null) return

            plan_3.text = result
        }
    }

    inner class HitAPITask9 : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String?): String? {
            //ここでAPIを叩きます。バックグラウンドで処理する内容です。
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            val buffer: StringBuffer

            try {
                //param[0]にはAPIのURI(String)を入れます(後ほど)。
                //AsynkTask<...>の一つめがStringな理由はURIをStringで指定するからです。
                val url = URL(params[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()  //ここで指定したAPIを叩いてみてます。

                //ここから叩いたAPIから帰ってきたデータを使えるよう処理していきます。

                //とりあえず取得した文字をbufferに。
                val stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                buffer = StringBuffer()
                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    buffer.append(line)
                    //Log.d("CHECK", buffer.toString())
                }

                //ここからは、今回はJSONなので、いわゆるJsonをParseする作業（Jsonの中の一つ一つのデータを取るような感じ）をしていきます。

                //先ほどbufferに入れた、取得した文字列
                val jsonText = buffer.toString()

                //JSONObjectを使って、まず全体のJSONObjectを取ります。
                val parentJsonObj = JSONObject(jsonText)
                //今回のJSONは配列になっているので（データは一つですが）、全体のJSONObjectから、getJSONArrayで配列"movies"を取ります。
                val parentJsonArray = parentJsonObj.getJSONArray("data")

                //JSONArrayの中身を取ります。映画"Your Name"のデータは、配列"movies"の０番目のデータなので、
                val detailJsonObj = parentJsonArray.getJSONObject(0)  //これもJSONObjectとして取得

                //moviesの0番目のデータのtitle項目をStringで取ります。これで中身を取れました。
                val movieName: String = detailJsonObj.getString("TITLE")  // => Your Name.

                //Stringでreturnしてあげましょう。
                return "$movieName"  // => Your Name. - 2016

                //ここから下は、接続エラーとかJSONのエラーとかで失敗した時にエラーを処理する為のものです。
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            //finallyで接続を切断してあげましょう。
            finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            //失敗した時はnullやエラーコードなどを返しましょう。
            return null
        }


        //返ってきたデータをビューに反映させる処理はonPostExecuteに書きます。これはメインスレッドです。
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result == null) return

            plan_4.text = result
        }
    }

    inner class HitAPITask10 : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String?): String? {
            //ここでAPIを叩きます。バックグラウンドで処理する内容です。
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            val buffer: StringBuffer

            try {
                //param[0]にはAPIのURI(String)を入れます(後ほど)。
                //AsynkTask<...>の一つめがStringな理由はURIをStringで指定するからです。
                val url = URL(params[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()  //ここで指定したAPIを叩いてみてます。

                //ここから叩いたAPIから帰ってきたデータを使えるよう処理していきます。

                //とりあえず取得した文字をbufferに。
                val stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                buffer = StringBuffer()
                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    buffer.append(line)
                    //Log.d("CHECK", buffer.toString())
                }

                //ここからは、今回はJSONなので、いわゆるJsonをParseする作業（Jsonの中の一つ一つのデータを取るような感じ）をしていきます。

                //先ほどbufferに入れた、取得した文字列
                val jsonText = buffer.toString()

                //JSONObjectを使って、まず全体のJSONObjectを取ります。
                val parentJsonObj = JSONObject(jsonText)
                //今回のJSONは配列になっているので（データは一つですが）、全体のJSONObjectから、getJSONArrayで配列"movies"を取ります。
                val parentJsonArray = parentJsonObj.getJSONArray("data")

                //JSONArrayの中身を取ります。映画"Your Name"のデータは、配列"movies"の０番目のデータなので、
                val detailJsonObj = parentJsonArray.getJSONObject(0)  //これもJSONObjectとして取得

                //moviesの0番目のデータのtitle項目をStringで取ります。これで中身を取れました。
                val movieName: String = detailJsonObj.getString("TITLE")  // => Your Name.

                //Stringでreturnしてあげましょう。
                return "$movieName"  // => Your Name. - 2016

                //ここから下は、接続エラーとかJSONのエラーとかで失敗した時にエラーを処理する為のものです。
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            //finallyで接続を切断してあげましょう。
            finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            //失敗した時はnullやエラーコードなどを返しましょう。
            return null
        }


        //返ってきたデータをビューに反映させる処理はonPostExecuteに書きます。これはメインスレッドです。
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result == null) return

            //plan_5.text = result
        }


    }



    }






