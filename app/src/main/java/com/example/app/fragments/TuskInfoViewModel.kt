package com.example.app.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.TuskInfo
import com.example.app.TuskInfoService
import com.example.app.TuskListener

class TuskInfoViewModel(
    private val tuskInfoService: TuskInfoService,
) : ViewModel() {

    private val _tusks = MutableLiveData<List<TuskInfo>>()
    val tusks: LiveData<List<TuskInfo>> = _tusks

    private val listener: TuskListener = {
        _tusks.value = it
    }

    init {
        tuskInfoService.addListener(listener)
        loadTusks()
    }

    override fun onCleared() {
        super.onCleared()
        tuskInfoService.removeListener(listener)
    }

    fun loadTusks() {
        tuskInfoService.addListener(listener)
    }

    fun deleteTusk(tuskInfo: TuskInfo) {
        tuskInfoService.removeTusk(tuskInfo)
    }


}