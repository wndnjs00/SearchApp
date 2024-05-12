package com.example.searchapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.searchapp.presentation.fragment.SearchFragment
import com.example.searchapp.R
import com.example.searchapp.data.model.DocumentResponse
import com.example.searchapp.presentation.fragment.StorageFragment
import com.example.searchapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBottomNavigation()

        setContentView(binding.root)
    }


    // BottomNavigation 표현함수
    private fun initBottomNavigation() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_, SearchFragment())
            .commitAllowingStateLoss()

        binding.bn.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.storage -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_,StorageFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

    }



}