package com.example.challengeroom1.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tbsiswa (
    @PrimaryKey(autoGenerate = true)
    val nis: Int,
    val nama: String,
    val kelas: String,
    val alamat: String
)