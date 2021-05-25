package com.example.myapplication

    fun create_month(today:String):String {
        var d = today.split("/")
        val year:String = d[0]
        var month:String = d[1]
        var day:String = d[2]

        val month_i: Int = d[1].toInt()
        if (month_i < 10) {
            month = "0" + d[1]

        }

        val day_i:Int = d[2].toInt()
        if(day_i < 10){
            day = "0" + d[2]

        }
        val number = year+ "/" + month+ "/" + day
        return  number
    }
