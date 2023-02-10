package com.example.challengeroom1.Room

import androidx.room.*

@Dao
interface tbsiswaDao {

    @Insert
    fun addtbsiswa(tbsiswa: tbsiswa)

    @Update
    fun updatetbsiswa(tbsiswa: tbsiswa)

    @Delete
    fun deletetbsiswa(tbsiswa: tbsiswa)

    @Query("SELECT * FROM tbsiswa")
    fun tampilsemua(): List<tbsiswa>

    @Query("SELECT * FROM tbsiswa WHERE nis=:tbsiswa_nis")
    fun tampilsemuaa(tbsiswa_nis: Int): List<tbsiswa>

}