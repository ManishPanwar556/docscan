package com.example.docscan.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["name","fileUri"])
data class PdfEntity(
    var name:String,
    var fileUri:String
)