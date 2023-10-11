package com.example.emill_p1_ap2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.emill_p1_ap2.data.entities.Division
import kotlinx.coroutines.flow.Flow

@Dao
interface DivisionDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(division: Division)
    @Query(""" SELECT * FROM Divisiones WHERE divisionId=:id LIMIT 1""")
    suspend fun find(id: Int) : Division
    @Delete
    suspend fun delete(division: Division)
    @Query("SELECT * FROM Divisiones")
    fun getAll(): Flow<List<Division>>
}