package com.matthew.carvalhodagenais.coinhuntingbuddy.dataobjects

data class Find(
    val year: String,
    val variety: String = "",
    val mintMark: String = "",
    val error: String = "",
    val findType: Int
)