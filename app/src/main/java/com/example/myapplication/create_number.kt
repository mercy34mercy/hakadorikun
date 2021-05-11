package com.example.myapplication



    fun create_number(year:Int,month:Int,day:Int,hour:Int,minute:Int):Int{
        val Year =  year *100000000
        val Month = month*1000000
        val Day =   day  *10000
        val Hour =  hour *100
        val number:Int = Year + Month + Day + Hour + minute

        return number
    }

