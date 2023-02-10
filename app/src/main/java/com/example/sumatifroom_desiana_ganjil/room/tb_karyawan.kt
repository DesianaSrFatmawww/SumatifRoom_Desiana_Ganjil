package com.example.sumatifroom_desiana_ganjil.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tb_karyawan (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val nama : String,
    val alamat : String,
    val usia : String,
)