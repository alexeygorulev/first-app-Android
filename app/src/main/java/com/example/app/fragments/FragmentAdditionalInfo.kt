package com.example.app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.app.*
import com.example.app.databinding.FragmentAdditionalInfoBinding
import com.example.app.fragments.constract.CustomAction
import com.example.app.fragments.constract.HasCustomAction
import com.example.app.fragments.constract.HasCustomTitle

class FragmentAdditionalInfo : Fragment(), HasCustomTitle, HasCustomAction {
    lateinit var binding: FragmentAdditionalInfoBinding
    private val viewModel: AdditionalInfoViewModel by viewModels { factory() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadUser(
            requireArguments().getInt(ARG_TUSK_ID), requireArguments().getBoolean(
                KEY_OPTIONS
            )
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAdditionalInfoBinding.inflate(inflater, container, false)
        viewModel.additionalInfo.observe(viewLifecycleOwner, Observer {
            binding.titleTusk.setText(it.tuskInfo.tuskTitle)
            binding.tuskDesc.setText(it.details)
        })
        return binding.root
    }

    private fun onConfirmPressed() {
        val title = binding.titleTusk.text.toString()
        val info = binding.tuskDesc.text.toString()
        val newItem = requireArguments().getBoolean(KEY_OPTIONS)
        if (newItem) {
            viewModel.createNewTusk(title, info)
        } else {
            viewModel.changeTusk(title, info)
        }

        navigator().goBack()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun getCustomAction(): CustomAction {
        return CustomAction(
            iconRes = R.drawable.ic_done,
            textRes = R.string.done,
            onCustomAction = Runnable {
                onConfirmPressed()
            }
        )
    }

    override fun getTitleRes(): Int = R.string.this_tusk
    private fun onOkPressed() {
        navigator().goBack()
    }

    companion object {
        @JvmStatic
        private val ARG_TUSK_ID = "ARG_TUSK_ID"

        @JvmStatic
        private val KEY_OPTIONS = "KEY_OPTIONS"

        @JvmStatic
        fun newInstance(tuskInfoId: Int, newItem: Boolean): FragmentAdditionalInfo {
            val fragment = FragmentAdditionalInfo()
            fragment.arguments = Bundle().apply {
                putInt(ARG_TUSK_ID, tuskInfoId)
                putBoolean(KEY_OPTIONS, newItem)
            }
            return fragment
        }

    }

}