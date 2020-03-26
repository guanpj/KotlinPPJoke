package com.me.guanpj.kotlin.ppjoke.ui.sofa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SofaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is soafa Fragment"
    }
    val text: LiveData<String> = _text
}