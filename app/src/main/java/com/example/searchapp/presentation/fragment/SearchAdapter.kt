package com.example.searchapp.presentation.fragment

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchapp.R
import com.example.searchapp.data.Constants
import com.example.searchapp.data.Dateformat
import com.example.searchapp.databinding.SearchItemBinding
import com.example.searchapp.data.model.DocumentResponse
import com.example.searchapp.data.model.SearchResponse
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale


class SearchAdapter(var searchList : ArrayList<DocumentResponse>, private val onClick : (DocumentResponse, Int) -> Unit) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){


    // 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return SearchViewHolder(SearchItemBinding.bind(view))
    }

    // 데이터 연결
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(searchList[position])

        holder.itemView.setOnClickListener {
            onClick(searchList[position], position)
        }
    }

    // 아이템 개수리턴
    override fun getItemCount(): Int = searchList.size


    // 외부에서 어뎁터에 데이터 배열을 넣어줌
    fun submitList(searchLists: ArrayList<DocumentResponse>){
        this.searchList = searchLists
    }


    class SearchViewHolder(private var binding : SearchItemBinding) : RecyclerView.ViewHolder(binding.root){
        private var currentItem : DocumentResponse?= null

        // 레이아웃과 데이터 연결
        fun bind(searchItem : DocumentResponse){
            currentItem = searchItem

            with(binding){
                cardViewSiteTv.text = currentItem!!.displaySiteName
                cardViewDateTv.text = Dateformat.dateformat(currentItem!!.datetime)

                Glide.with(itemView.context)
                    .load(currentItem!!.thumbnailUrl)
                    .placeholder(R.drawable.base_photo_img)
                    .into(cardViewImg)

                // 빈북마크 클릭시
                cardViewBookmarkImg.setOnClickListener {

                    if(currentItem!!.isLike == false){
                        cardViewBookmarkImg.setImageResource(R.drawable.bookmark_full_img)
                        currentItem!!.isLike = true     //클릭됐으니깐 true로 바꿔주기
                    }else{
                        cardViewBookmarkImg.setImageResource(R.drawable.bookmark_img)
                        currentItem!!.isLike = false
                    }
                    Log.d("bookmark_click", currentItem!!.isLike.toString())
                }

            }
        }
    }


}

