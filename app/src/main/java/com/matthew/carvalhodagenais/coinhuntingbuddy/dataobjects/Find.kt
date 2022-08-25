package com.matthew.carvalhodagenais.coinhuntingbuddy.dataobjects

import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.CoinTypes

data class Find(
    val year: String,
    val variety: String = "",
    val mintMark: String = "",
    val error: String = "",
    val grade: String = "",
    val findType: CoinTypes
)