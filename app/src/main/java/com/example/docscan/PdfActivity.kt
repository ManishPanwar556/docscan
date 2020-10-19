package com.example.docscan

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.widget.Toast

class PdfActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)
        val actionBar=supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFBB33")))
        createFile("manish")
    }
    private fun createFile(filename:String){
       val dir=getExternalFilesDir("Document/Scanner")
        dir?.listFiles()?.forEach {
            
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
}