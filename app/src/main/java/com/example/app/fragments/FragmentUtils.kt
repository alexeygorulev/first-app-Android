package com.example.app.fragments

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app.App
import com.example.app.fragments.constract.Navigator

class ViewModelFactory(
    private val app: App,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            TuskInfoViewModel::class.java -> {
                TuskInfoViewModel(app.tuskInfoService)
            }
            AdditionalInfoViewModel::class.java -> {
                AdditionalInfoViewModel(app.tuskInfoService)
            }
            else -> {
                throw java.lang.IllegalStateException("kek")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)

fun Fragment.navigator() = requireActivity() as Navigator