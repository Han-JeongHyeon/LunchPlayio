package com.example.flow.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lunch (
    @PrimaryKey val d_id: String,
    val id: String,
    val is_checked: Boolean,
    val user_id: String
)