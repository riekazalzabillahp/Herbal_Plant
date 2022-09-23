package com.rieka.herbaldetector.ui.clasify

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import com.rieka.herbaldetector.databinding.ActivityViewImageBinding
import com.rieka.herbaldetector.ui.createCustomTempFile
import com.rieka.herbaldetector.ui.detector.DetectorActivity
import com.rieka.herbaldetector.ui.detector.DetectorActivity.Companion.KEY_IS_TAKE
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class ViewImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewImageBinding
    private val viewModel: ClassifyViewModel by viewModels()
    private var image: File? = null
    private var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeData()
        binding.btnTakephoto.setOnClickListener {
            val i = Intent(this, DetectorActivity::class.java)
            startActivityForResult(i, DETECTOR_ACTIVITY_REQUEST_CODE)
        }

        binding.btnGallery.setOnClickListener {
            startGallery()
        }

        binding.btnSubmit.setOnClickListener {
            image?.let { img -> viewModel.getPrediction(img) }
        }
    }

    private fun observeData() {
        viewModel.herbal.observe(this) {
            Toast.makeText(this, "Prediksi : $it", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == DETECTOR_ACTIVITY_REQUEST_CODE) {
            if (resultCode === RESULT_OK) {
                val isTake = data?.extras?.getBoolean(KEY_IS_TAKE) ?: false
                if (isTake) {
                    Toast.makeText(this, "LOAD", Toast.LENGTH_SHORT).show()
                    val imgString = getExternalFilesDir("/Pictures/bitmap_test.jpg") as File
                    image = imgString
                    Log.d("CAMERA", imgString.toString())
                    binding.imageview.setImageBitmap(
                        BitmapFactory.decodeFile(imgString?.path)
                    )
                }
            }
        }
    }

    fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            image = myFile
            binding.imageview.setImageURI(selectedImg)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            image = myFile
            binding.imageview.setImageURI(selectedImg)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    companion object {
        const val DETECTOR_ACTIVITY_REQUEST_CODE = 1
    }
}