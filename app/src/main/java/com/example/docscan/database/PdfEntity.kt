package com.example.docscan.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PdfEntity(
    var name:String,
    var fileUri:String,
    @PrimaryKey(autoGenerate = true)
    var id:Long=0L
)