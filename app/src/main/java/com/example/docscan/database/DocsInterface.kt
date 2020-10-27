package com.example.docscan.database

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.room.*

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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPdf(pdfEntity: PdfEntity)
    @Delete
    suspend fun deletePdf(pdfEntity: PdfEntity)
    @Query("DELETE FROM pdfentity")
    suspend fun deleteAllPdf()
    @Query("DELETE FROM pdfentity WHERE fileUri=:uri")
    suspend fun deletePdf(uri:String)
    @Query("SELECT *FROM pdfentity")
    fun getPdfList():LiveData<List<PdfEntity>>
}