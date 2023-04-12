package com.example.app.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.*
import com.example.app.databinding.FragmentMainBinding

class FragmentMain : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: TuskInfoAdapter
    lateinit var tuskInfoService: TuskInfoService
    var newId: Int? = null
    private val viewModel: TuskInfoViewModel by viewModels { factory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().startService(Intent(context, TuskInfoService::class.java))
        tuskInfoService = TuskInfoService()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = TuskInfoAdapter(object : TuskActionListener {
            override fun onTuskRemove(tuskInfo: TuskInfo) = viewModel.deleteTusk(tuskInfo)
            override fun onDescribeScreen(tuskInfo: TuskInfo) {
                navigator().showAdditionalInfoScreen(tuskInfo.id, false)
            }
        })

        val manager = LinearLayoutManager(requireContext())
        binding.rcView.layoutManager = manager
        binding.rcView.adapter = adapter

        viewModel.tusks.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            newId = it.size + 1
        })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.additionalButton.setOnClickListener {
            navigator().showAdditionalInfoScreen(newId!!, true)
        }

    }


    companion object {

        @JvmStatic
        private val KEY_OPTIONS = "KEY_OPTIONS"

    }

}