package com.example.flow.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Lunch::class], version = 1, exportSchema = false)
abstract class Database(): RoomDatabase() {

    abstract fun lunchInfo(): Dao

}