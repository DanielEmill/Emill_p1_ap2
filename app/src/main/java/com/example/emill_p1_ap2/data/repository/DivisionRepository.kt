package com.example.emill_p1_ap2.data.repository

import com.example.emill_p1_ap2.data.dao.DivisionDao
import com.example.emill_p1_ap2.data.entities.Division
import javax.inject.Inject

class DivisionRepository @Inject constructor(private val divisionDao: DivisionDao) {
    suspend fun save(division: Division) = divisionDao.save(division)
    suspend fun delete(division: Division) = divisionDao.delete(division)
    suspend fun find(Id: Int) = divisionDao.find(Id)
    fun getAll() = divisionDao.getAll()
}