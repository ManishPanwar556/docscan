package com.example.docscan.ui

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.docscan.R
import com.example.docscan.adapter.MyAdapter
import com.example.docscan.database.DocsEntity
import com.example.docscan.viewModel.DocsViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    val viewModel by lazy {
        DocsViewModel(application)
    }
    var moveUp: Boolean = true

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 10
        const val GALLERY_REQUEST_CODE = 20
        const val TAG = "Scanner"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar=supportActionBar

        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFBB33")))
        addbtn.setOnClickListener {
            if (moveUp) {
                animateUp()

            } else {
                animateDown()

            }
        }
        camerabtn.setOnClickListener {
            openCamera()
            animateDown()
        }
        gallerybtn.setOnClickListener {
            openImages()
            animateDown()
        }
        createpdf.setOnClickListener {
            animateDown()
            createPdf()

        }
        pdfbtn.setOnClickListener {
//            start the activity to show list of all pdfs
            val intent=Intent(this, PdfActivity::class.java)
            startActivity(intent)
            animateDown()
        }
        viewModel.properties.observe(this, Observer {
            rev.layoutManager = GridLayoutManager(this, 3)
            rev.adapter = MyAdapter(it)

        })
    }

    private fun animateUp() {
        ObjectAnimator.ofFloat(linear1, "translationY", -180f).apply {
            text1.visibility = View.VISIBLE
            duration = 200
            start()
        }
        ObjectAnimator.ofFloat(linear2, "translationY", -360f).apply {
            text2.visibility = View.VISIBLE
            duration = 200
            start()
        }
        ObjectAnimator.ofFloat(linear3, "translationY", -540f).apply {
            text3.visibility = View.VISIBLE
            duration = 200
            start()
        }
        ObjectAnimator.ofFloat(linear4, "translationY", -740f).apply {
            text4.visibility = View.VISIBLE
            duration = 200
            start()
        }
        moveUp = false
    }

    private fun animateDown() {
        ObjectAnimator.ofFloat(linear1, "translationY", 0f).apply {
            text1.visibility = View.INVISIBLE
            duration = 200
            start()
        }
        ObjectAnimator.ofFloat(linear2, "translationY", 0f).apply {
            text2.visibility = View.INVISIBLE
            duration = 200
            start()
        }
        ObjectAnimator.ofFloat(linear3, "translationY", 0f).apply {
            text3.visibility = View.INVISIBLE
            duration = 200
            start()
        }
        ObjectAnimator.ofFloat(linear4, "translationY", 0f).apply {
            text4.visibility = View.INVISIBLE
            duration = 200
            start()
        }
        moveUp = true
    }

    private fun createFile(): File {
        val file = File(
            applicationContext.getExternalFilesDir("Document"),
            "Scanner"
        )
        if (!file.exists()) {
            file.mkdir()
        }
        Toast.makeText(this, "$file", Toast.LENGTH_LONG).show()
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
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                viewModel.insertData(DocsEntity(result.uri.path, ""))

            }
        }
    }

    private fun delete() {
        viewModel.deleteAll()
    }

    private fun launchImageCrop(uri: Uri) {
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON).start(this)
    }

    private fun createPdf() {
        var count = 1
        val document = PdfDocument()

        viewModel.properties.value?.forEach{

            val bitmap=BitmapFactory.decodeFile(it.uri)
            val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, count).create()
            val page = document.startPage(pageInfo)
            val canvas = page.canvas
            canvas.drawBitmap(bitmap, pageInfo.contentRect, pageInfo.contentRect, null)
            document.finishPage(page)
            count++
        }
        val dir = createFile()
        val file = File(dir, "${System.currentTimeMillis()}.pdf")
        try {
            val fileOutputStream = FileOutputStream(file)
            document.writeTo(fileOutputStream)
        } catch (exc: FileNotFoundException) {
            Toast.makeText(this, "File not made", Toast.LENGTH_LONG).show()
        }


    }
}