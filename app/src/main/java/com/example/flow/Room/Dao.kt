package com.example.flow.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update

@Dao
interface Dao {

    @Insert
    suspend fun lunchInsert(vararg lunch: Lunch)

    @Update
    suspend fun lunchUpdate(vararg lunch: Lunch)

}