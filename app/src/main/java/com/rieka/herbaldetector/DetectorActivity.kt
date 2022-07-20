package com.rieka.herbaldetector

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture
import com.rieka.herbaldetector.databinding.ActivityDetectorBinding
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetectorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetectorBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraSelector: CameraSelector
    private var imageCapture: ImageCapture? = null
    private lateinit var imgCaptureExecutor: ExecutorService
    private val cameraPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            if (permissionGranted) {
                startCamera()
            } else {
                Snackbar.make(
                    binding.root,
                    "The camera permission is necessary",
                    Snackbar.LENGTH_INDEFINITE
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        imgCaptureExecutor = Executors.newSingleThreadExecutor()

        cameraPermissionResult.launch(android.Manifest.permission.CAMERA)

        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        startCamera()

        binding.btnCamera.setOnClickListener {
            takePhoto()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                animateFlash()
            }
        }
    }

    private fun startCamera() {
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(binding.camera.surfaceProvider)
        }
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Log.d(TAG, "Use case binding failed")
            }
        }, ContextCompat.getMainExecutor(this))
    }


//    private fun takePhoto() {
//        imageCapture?.let {
//            val fileName = "JPEG_${System.currentTimeMillis()}.jpg"
//            val file = File(externalMediaDirs[0], fileName)
//            val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()
//            it.takePicture(
//                outputFileOptions,
//                imgCaptureExecutor,
//                object : ImageCapture.OnImageSavedCallback {
//                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                        Log.i(TAG, "The image has been saved in ${file.toUri()}")
//                    }
//
//                    override fun onError(exception: ImageCaptureException) {
//                        Toast.makeText(
//                            binding.root.context,
//                            "Error taking photo",
//                            Toast.LENGTH_LONG
//                        ).show()
//                        Log.d(TAG, "Error taking photo:$exception")
//                    }
//
//                })
//        }
//    }

    private fun takePhoto() {
        binding.camera.bitmap?.let { File(externalMediaDirs[0], "bitmap_test.jpg").writeBitmap(it, Bitmap.CompressFormat.JPEG, 100) }
    }

    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun animateFlash() {
        binding.root.postDelayed({
            binding.root.foreground = ColorDrawable(Color.WHITE)
            binding.root.postDelayed({
                binding.root.foreground = null
            }, 50)
        }, 100)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}