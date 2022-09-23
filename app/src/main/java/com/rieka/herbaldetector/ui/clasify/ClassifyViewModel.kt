package com.rieka.herbaldetector.ui.clasify

import androidx.lifecycle.ViewModel
import com.rieka.herbaldetector.data.Repository
import java.io.File

class ClassifyViewModel : ViewModel() {
    private val repository: Repository = Repository()

    fun getPrediction(file: File) : String =
        repository.getPrediction(file)

}