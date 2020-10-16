package com.example.docscan.viewModel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.docscan.database.Appdatabase
import com.example.docscan.database.DocsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DocsViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var db: Appdatabase

    lateinit var properties: LiveData<List<DocsEntity>>

    init {

        db =
            Room.databaseBuilder(application.applicationContext, Appdatabase::class.java, "docs_db")
                .fallbackToDestructiveMigration()
                .build()
        properties = getAllData()
    }

    fun insertData(docsEntity: DocsEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            db.docsDao().insertUri(docsEntity)
        }
        properties = getAllData()
    }

    fun getAllData(): LiveData<List<DocsEntity>> {
        return db.docsDao().getList()
    }
    fun deleteAll(){
        GlobalScope.launch(Dispatchers.IO) {
            db.docsDao().deleteAll()
        }

    }

}