package com.hds.labs.ids.hdslabs.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hds.labs.ids.hdslabs.R
import com.hds.labs.ids.hdslabs.databinding.FragmentHomeBinding
import com.hds.labs.ids.hdslabs.model.MainItem
import com.hds.labs.ids.hdslabs.model.MainItemAdapter


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private var mItemList: ArrayList<MainItem> = ArrayList()
    private lateinit var mListAdapter: MainItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val navController = findNavController()
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)


        mainTopItems()
        return binding.root
    }


    private fun mainTopItems() {

        mItemList.add(MainItem(R.drawable.medical_report, "View Reports", 0))
        mItemList.add(MainItem(R.drawable.search_test, "Test List", 1))
        mItemList.add(MainItem(R.drawable.delivery_boy, "Home Sampling", 2))
        mItemList.add(MainItem(R.drawable.search_test, "Find Test", 3))


        mListAdapter = MainItemAdapter(mItemList, requireContext()) {
            onPositionClicks(it.pos)
        }
        binding.optionRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            setHasFixedSize(true)
            adapter = mListAdapter
        }

    }

    private fun onPositionClicks(position:Int) {
        when(position){
            4->{
                val action = HomeFragmentDirections.actionHomeFragmentMainToLocationFragmentMain()
                findNavController().navigate(action,navOptions { // Use the Kotlin DSL for building NavOptions
                    anim {
                        enter = android.R.animator.fade_in
                        exit = android.R.animator.fade_out
                    }
                })
         /*       val item = BottomNavigationMenuView.menu.findItem(R.id.reportsFragment)
                NavigationUI.onNavDestinationSelected(item, navController)*/
            }
            5->{
                findNavController().navigate(R.id.aboutFragmentMain)
            }

        }

    }


}