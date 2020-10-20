package com.example.docscan.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docscan.ClickInterface
import com.example.docscan.R
import com.example.docscan.adapter.PdfAdapter
import com.example.docscan.database.PdfEntity
import com.example.docscan.viewModel.DocsViewModel
import kotlinx.android.synthetic.main.activity_pdf.*

class PdfActivity : AppCompatActivity(),ClickInterface {
    val viewModel by lazy {
        DocsViewModel(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)
        val actionBar=supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFBB33")))
        insertPdfData()
        viewModel.pdfProperties.observe(this,Observer{
            rev2.adapter=PdfAdapter(it,this)
            rev2.layoutManager=LinearLayoutManager(this)
        })

    }
    private fun insertPdfData(){
       val dir=getExternalFilesDir("Document/Scanner")
       dir?.listFiles()?.forEach {
           viewModel.insertPdfData(PdfEntity(it.name,it.path))
       }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==123){
            data?.data.let {
                Toast.makeText(this,"$it",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(uri: String,position:Int) {
        val dir=getExternalFilesDir("Document/Scanner")
        val file=dir?.listFiles()?.get(position)

        val uri=FileProvider.getUriForFile(this,getString(R.string.file_provider_authority),file!!)
        val target=Intent(Intent.ACTION_VIEW)
        target.setDataAndType(uri,"application/pdf")
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        val intent=Intent.createChooser(target,"Open File")
        try{
           startActivity(intent)
        }
        catch (exc:ActivityNotFoundException){

        }

    }
}