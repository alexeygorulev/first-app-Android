package com.example.app

data class TuskInfo(
    val id: Int, val tuskTitle: String, val info: String? = null
)

data class TuskInfoDetails(
    val tuskInfo: TuskInfo,
    val details: String,
)
