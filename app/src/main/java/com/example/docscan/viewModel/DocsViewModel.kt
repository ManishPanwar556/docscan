package com.example.docscan.viewModel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.docscan.database.Appdatabase
import com.example.docscan.database.DocsEntity
import com.example.docscan.database.PdfEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DocsViewModel(application: Application) : AndroidViewModel(application) {

    private var db: Appdatabase

    var properties: LiveData<List<DocsEntity>>

    var pdfProperties: LiveData<List<PdfEntity>>

    private val _deleteImages = MutableLiveData<Boolean>()

    val deleteImages: LiveData<Boolean>
        get() = _deleteImages

    init {

        db =
            Room.databaseBuilder(application.applicationContext, Appdatabase::class.java, "docs_db")
                .fallbackToDestructiveMigration()
                .build()
        properties = getAllData()
        pdfProperties = getAllPdf()
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
    fun deleteDocsEntity(docsEntity: DocsEntity){
        GlobalScope.launch(Dispatchers.IO) {
            db.docsDao().delete(docsEntity)
        }
    }
    fun deleteAll() {
        GlobalScope.launch(Dispatchers.IO) {
            db.docsDao().deleteAll()
        }
    }

    fun insertPdfData(pdfEntity: PdfEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            db.docsDao().insertPdf(pdfEntity)
        }
        pdfProperties = getAllPdf()
    }

    fun delete(pdfEntity: PdfEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            db.docsDao().deletePdf(pdfEntity)
        }
    }
    fun deleteAllImages(){
        _deleteImages.value=true

    }
    fun deleteAllPdf() {
        GlobalScope.launch(Dispatchers.IO) {
            db.docsDao().deleteAllPdf()
        }
    }

    fun getAllPdf(): LiveData<List<PdfEntity>> {
        return db.docsDao().getPdfList()
    }

    fun deletePdf(uri: String) {
        GlobalScope.launch(Dispatchers.IO) {
            db.docsDao().deletePdf(uri)
        }
    }
}