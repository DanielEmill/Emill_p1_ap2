package com.example.emill_p1_ap2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.emill_p1_ap2.data.dao.DivisionDao
import com.example.emill_p1_ap2.data.entities.Division

@Database(entities = [Division::class], version = 1)
abstract class DivisionDb : RoomDatabase() {
    abstract fun divisionDao(): DivisionDao
}