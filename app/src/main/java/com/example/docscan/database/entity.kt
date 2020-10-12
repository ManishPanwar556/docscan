package com.example.docscan.database

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DocsEntity(
    val uri:String,
    @PrimaryKey(autoGenerate = true)
    var id:Long=0L,

)