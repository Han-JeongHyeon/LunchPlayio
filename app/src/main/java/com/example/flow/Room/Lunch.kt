package com.example.flow.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lunch (
    @PrimaryKey val date: String,
    val checked: Boolean
)