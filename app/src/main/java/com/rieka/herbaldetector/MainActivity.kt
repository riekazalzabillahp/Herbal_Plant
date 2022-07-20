package com.rieka.herbaldetector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDetector : Button = findViewById(R.id.btnDetector)

        btnDetector.setOnClickListener {
            startActivity(Intent(this, DetectorActivity::class.java))
        }

        val btnGallery : Button = findViewById(R.id.btnGallery)

        btnGallery.setOnClickListener {
            startActivity(Intent(this, GalleryActivity::class.java))
        }

//        val btnDetector : Button = findViewById(R.id.detector)
//
//        btnDetector.setOnClickListener {
//            startActivity(Intent(this, DetectorActivity::class.java))
//        }
    }

}