package com.example.app.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.TuskInfoDetails
import com.example.app.TuskInfoNotFoundException
import com.example.app.TuskInfoService

class AdditionalInfoViewModel(
    private val tuskInfoService: TuskInfoService,
) : ViewModel() {

    private val _additionalInfo = MutableLiveData<TuskInfoDetails>()
    val additionalInfo: LiveData<TuskInfoDetails> = _additionalInfo

    fun loadUser(tuskId: Int, newItem: Boolean) {
        if (_additionalInfo.value != null) return
        try {
            if (newItem) {
                this._additionalInfo.value = tuskInfoService.getNewTusk(tuskId)
            } else {
                this._additionalInfo.value = tuskInfoService.getTuskById(tuskId)
            }
        } catch (e: TuskInfoNotFoundException) {
            e.printStackTrace()
        }
    }


    fun changeTusk(title: String, info: String) {
        val additionalInfo = additionalInfo.value ?: return
        tuskInfoService.changeTusk(additionalInfo.tuskInfo, title, info)
    }
    fun createNewTusk(title: String, info: String) {
        val additionalInfo = additionalInfo.value ?: return
        tuskInfoService.createTusk(additionalInfo.tuskInfo.id, title, info)

    }
}