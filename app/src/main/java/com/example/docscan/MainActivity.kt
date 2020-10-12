package com.example.docscan

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.docscan.adapter.MyAdapter
import com.example.docscan.database.DocsEntity
import com.example.docscan.viewModel.DocsViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.nio.file.Files

class MainActivity : AppCompatActivity() {
    val viewModel by lazy {
        DocsViewModel(application)
    }
    companion object {
        const val REQUEST_IMAGE_CAPTURE = 10
        const val GALLERY_REQUEST_CODE = 20
        const val TAG = "Scanner"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createFile()
        camerabtn.setOnClickListener {
            openCamera()
        }
        imagebtn.setOnClickListener {
            openImages()
        }
        viewModel.properties.observe(this, Observer {
             rev.layoutManager=GridLayoutManager(this,3)
            rev.adapter=MyAdapter(it)

        })
    }
  private fun createFile(): File {
      val dir = applicationContext.getExternalFilesDir("")

      val file = File(dir, "SCANNER")
      if(!file.exists()){
          file.mkdir()
      }
      Toast.makeText(this,"File ${file}",Toast.LENGTH_LONG).show()
      return file
  }
    private fun openImages() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, "${e}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                        if (uri != null) {
                            launchImageCrop(uri)
                        }
                    }
                } else {
                    Log.e(TAG, "CANNOT GET IMAGE")
                }
            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE->{
                val result=CropImage.getActivityResult(data)
                if(resultCode==Activity.RESULT_OK){
                    viewModel.insertData(DocsEntity(result.uri.toString()))
                }
            }
        }
    }

    private fun launchImageCrop(uri: Uri) {
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON).start(this)

    }
}