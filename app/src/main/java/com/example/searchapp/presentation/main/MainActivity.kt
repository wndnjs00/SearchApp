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

        setFragment(SearchFragment())

        binding.bn.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.search -> {
                    setFragment(SearchFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.storage -> {
                    setFragment(StorageFragment())
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

    }


    private fun setFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_, fragment)
            .commit()
    }



}