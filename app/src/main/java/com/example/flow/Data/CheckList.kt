package com.example.flow.Data

data class CheckList(
    val data: Data
)

data class Data(
    val id: String,
    val user_id: String,
    val d_id: String?,
    val is_checked: Int
)