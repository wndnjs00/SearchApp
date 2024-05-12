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

    // 좋아요를 눌러 선택한 아이템을 저장하는 빈리스트 (공유해서 사용할것임)
    var likedItems : ArrayList<DocumentResponse> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBottomNavigation()

        setContentView(binding.root)
    }


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


    // 좋아요가 눌린 아이템을 likedItems 리스트에 추가하는 함수
    fun addLikeItem(item : DocumentResponse){
        likedItems.add(item)
    }

    // 좋아요가 눌린 아이템을 likedItems 리스트에서 삭제하는 함수
    fun removeLikeItem(item: DocumentResponse){
        likedItems.remove(item)
    }

}