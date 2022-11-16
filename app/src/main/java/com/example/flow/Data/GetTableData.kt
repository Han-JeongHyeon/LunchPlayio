package com.example.flow.Data

data class GetTableData(
    val id: String,
    val is_checked: String,
    val d_id: String?,
    val date: String,
    val is_availiable: String,
    val cnt: String,
    var user_id: String
)