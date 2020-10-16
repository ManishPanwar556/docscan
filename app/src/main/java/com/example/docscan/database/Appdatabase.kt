package com.example.docscan.database

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [DocsEntity::class],version = 2)
abstract class Appdatabase: RoomDatabase() {
    abstract fun docsDao():DocsInterface
}