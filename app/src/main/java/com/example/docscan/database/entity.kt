package com.example.docscan.database

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DocsEntity(
    val uri:String,
    val bitmap: String,
    @PrimaryKey(autoGenerate = true)
    var id:Long=0L,

)