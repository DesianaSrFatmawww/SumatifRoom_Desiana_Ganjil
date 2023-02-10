package com.example.sumatifroom_desiana_ganjil.room

import androidx.room.*

@Dao
interface karyawanDAO {

    @Insert
    fun addTbKaryawan(tbkaryawan : tb_karyawan)

    @Update
    fun updateTbKaryawan(tbkaryawan : tb_karyawan)

    @Delete
    fun deleteTbKaryawan(tbkaryawan : tb_karyawan)

    @Query("SELECT * FROM tb_karyawan")
    fun getTbKaryawan():List<tb_karyawan>

    @Query("SELECT * FROM tb_karyawan WHERE id =:id_karyawan")
    fun TampilTbKaryawan(id_karyawan:Int):List<tb_karyawan>

    @Query("SELECT * FROM tb_karyawan ORDER BY id ASC")
    fun urut():List<tb_karyawan>
}