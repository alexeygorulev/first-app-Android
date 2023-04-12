package com.example.app.fragments.constract

import android.app.TaskInfo
import androidx.fragment.app.Fragment
import android.os.Parcelable
import androidx.lifecycle.LifecycleOwner
import com.example.app.TuskInfo
import com.example.app.TuskListener

typealias ResultListener<T> = (T) -> Unit


interface Navigator {

    fun showAdditionalInfoScreen(id: Int, newItem: Boolean)



    fun goBack()



}