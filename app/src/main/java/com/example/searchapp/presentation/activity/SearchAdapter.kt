package com.example.searchapp.presentation.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchapp.R
import com.example.searchapp.databinding.SearchItemBinding
import com.example.searchapp.retrofit.model.DocumentResponse


class SearchAdapter(var searchList : ArrayList<DocumentResponse>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){

    // 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return SearchViewHolder(SearchItemBinding.bind(view))
    }

    // 데이터 연결
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(searchList[position])
    }

    // 아이템 개수리턴
    override fun getItemCount(): Int {
        return searchList.size
    }


    class SearchViewHolder(private var binding : SearchItemBinding) : RecyclerView.ViewHolder(binding.root){
        private var currentItem : DocumentResponse ?= null

        // 레이아웃과 데이터 연결
        fun bind(searchItem : DocumentResponse){
            currentItem = searchItem

            binding.cardViewSiteTv.text = currentItem!!.displaySiteName
            binding.cardViewDateTv.text = currentItem!!.datetime

            Glide.with(itemView.context)
                .load(currentItem!!.thumbnailUrl)
                .into(binding.cardViewImg)
        }
    }


}