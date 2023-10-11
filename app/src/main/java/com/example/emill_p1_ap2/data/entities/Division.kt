package com.example.emill_p1_ap2.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Divisiones")
data class Division(
    @PrimaryKey
    val divisionId: Int? = null,
    var nombre: String = "",
    var dividiendo: Int? = 0,
    var divisor: Int? = 0,
    var cociente: Int? = 0,
    var residuo: Int? = 0,
)

