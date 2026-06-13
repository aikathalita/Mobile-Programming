package com.example.studentregistrationapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.studentregistrationapp.data.local.entity.Siswa
import kotlinx.coroutines.flow.Flow

@Dao
interface SiswaDao {
    @Insert
    suspend fun insertSiswa(siswa: Siswa)

    @Update
    suspend fun updateSiswa(siswa: Siswa)

    @Delete
    suspend fun deleteSiswa(siswa: Siswa)

    @Query("SELECT * FROM siswa ORDER BY id DESC")
    fun getAllSiswa(): Flow<List<Siswa>>
}