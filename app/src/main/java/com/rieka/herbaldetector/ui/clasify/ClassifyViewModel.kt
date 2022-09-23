package com.rieka.herbaldetector.ui.clasify

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rieka.herbaldetector.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class ClassifyViewModel : ViewModel() {
    private val repository: Repository = Repository()

    private val _herbal = MutableLiveData<String>()
    val herbal : LiveData<String> = _herbal

    fun getPrediction(file: File) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getPrediction(file)
            if (result.isSuccessful) {
                _herbal.postValue(result.body())
            }
        }
    }

}