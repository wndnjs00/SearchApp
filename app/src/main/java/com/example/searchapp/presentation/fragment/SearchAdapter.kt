package com.example.searchapp.presentation.fragment

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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

                // cardViewBookmarkFullImg의 visibility는 currentItem!!.isLike = ture일때만 보이도록
                // 즉, 클릭했을때만 색칠한 북마크 보이도록
                cardViewBookmarkFullImg.visibility =
                    if (currentItem!!.isLike) {
                        View.VISIBLE
                    } else {
                        View.INVISIBLE
                    }


            }
        }
    }


    // 외부에서 어뎁터에 데이터 배열을 넣어줌
    fun submitList(searchLists : ArrayList<DocumentResponse>){
        searchList = searchLists
    }

    // 아이템 클리어해주는 함수
    fun clearItem(){
        searchList.clear()
    }

}

