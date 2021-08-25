package com.example.myapplication

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
class encode_json(val name: String, val language: String)
fun main() {
    val data = Json.decodeFromString<encode_json>("""
        {"name":"kotlinx.serialization","language":"Kotlin"}
    """)
    println(data)
}