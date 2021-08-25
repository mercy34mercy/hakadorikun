package com.example.myapplication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class sample_data_2_2(
    @SerialName("task")
    val task:Array<sample_data_2>
)

@Serializable
data class sample_data_2(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("tel")
    val tel: String = ""
)
