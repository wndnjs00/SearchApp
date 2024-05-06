package com.example.searchapp

import android.media.Image
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
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
            add(ImageSearchFragment())
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