package com.example.docscan.database

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DocsInterface {
    @Insert
    suspend fun insertUri(docs:DocsEntity)
    @Delete
    suspend fun delete(docs:DocsEntity)
    @Query("DELETE FROM docsentity")
    suspend fun deleteAll()
    @Query("SELECT *FROM docsentity")
    fun getList():LiveData<List<DocsEntity>>
    @Insert
    suspend fun insertPdf(pdfEntity: PdfEntity)
    @Delete
    suspend fun deletePdf(pdfEntity: PdfEntity)
    @Query("DELETE FROM pdfentity")
    suspend fun deleteAllPdf()
    @Query("SELECT *FROM pdfentity")
    fun getPdfList():LiveData<List<PdfEntity>>
}