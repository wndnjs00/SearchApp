package com.example.searchapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.searchapp.presentation.fragment.SearchFragment
import com.example.searchapp.R
import com.example.searchapp.presentation.fragment.StorageFragment
import com.example.searchapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPager()

        setContentView(binding.root)
    }


    private fun viewPager() {
        val fragmentList = ArrayList<Fragment>().apply {
            add(SearchFragment())
            add(StorageFragment())
        }
        binding.viewPager.adapter = ViewPagerAdapter(fragmentList, this)

        // tabLayout
        val tabTitle = listOf(getString(R.string.tablayout_search), getString(R.string.tablayout_storage))
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }
}