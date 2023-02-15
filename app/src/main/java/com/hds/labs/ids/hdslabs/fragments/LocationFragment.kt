package com.hds.labs.ids.hdslabs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hds.labs.ids.hdslabs.databinding.FragmentHomeBinding
import com.hds.labs.ids.hdslabs.databinding.FragmentLocationBinding


class LocationFragment : Fragment() {
    lateinit var binding: FragmentLocationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val navController = findNavController()
        binding = FragmentLocationBinding.inflate(layoutInflater,container,false)

        return binding.root
    }


}