package com.example.myapplication

import java.time.LocalDateTime

    fun generate(date: String,hour:Int,minute:Int):LocalDateTime{
        val d: List<String> = date.split("/")
        val d2:String = d[0] +"-"+d[1]+"-"+d[2]

        val day = LocalDateTime.of(d[0].toInt(),d[1].toInt(),d[2].toInt(),hour.toInt(),minute.toInt(),0)

        return day
    }
