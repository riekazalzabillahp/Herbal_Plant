package com.rieka.herbaldetector

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.rieka.herbaldetector.databinding.ActivityResultBinding
import com.rieka.herbaldetector.model.Tanaman

class ResultActivity : AppCompatActivity() {

    lateinit var strNamaTanaman: String
    lateinit var strManfaatTanaman: String
    lateinit var modelMain: Tanaman

    private val binding: ActivityResultBinding by lazy {
        ActivityResultBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //set transparent statusbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

        val htmlAsString = this.getString(R.string.bidara)

        val htmlAsSpanned = Html.fromHtml(htmlAsString) // used by TextView
        binding.tvCaraOlah.text = htmlAsSpanned

        // set the html content on a TextView

        // set the html content on a TextView

//        setSupportActionBar(toolbar)
//        assert(supportActionBar != null)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowTitleEnabled(false)

        //get data intent
//        modelMain = intent.getSerializableExtra(DETAIL_TANAMAN) as ModelMain?
//        if (modelMain != null) {
//            strNamaTanaman = modelMain.nama
//            strManfaatTanaman = modelMain.deskripsi
//            Glide.with(this)
//
//                .load(modelMain.image)
//                .into(imageTanaman)
//
//            tvNamaTanaman.setText(strNamaTanaman)
//            tvManfaatTanaman.setText(strManfaatTanaman)
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val DETAIL_TANAMAN = "DETAIL_TANAMAN"
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window = activity.window
            val layoutParams = window.attributes
            if (on) {
                layoutParams.flags = layoutParams.flags or bits
            } else {
                layoutParams.flags = layoutParams.flags and bits.inv()
            }
            window.attributes = layoutParams
        }
    }
}