package com.example.searchapp.presentation.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchapp.R
import com.example.searchapp.data.Dateformat
import com.example.searchapp.databinding.SearchItemBinding
import com.example.searchapp.data.model.DocumentResponse


class StorageAdapter(var searchList : ArrayList<DocumentResponse>, private val onClick : (DocumentResponse, Int) -> Unit) : RecyclerView.Adapter<StorageAdapter.StorageViewHolder>(){

    // 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return StorageViewHolder(SearchItemBinding.bind(view))
    }

    // 데이터 연결
    override fun onBindViewHolder(holder: StorageViewHolder, position: Int) {
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


    class StorageViewHolder(private var binding : SearchItemBinding) : RecyclerView.ViewHolder(binding.root){
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
            }
        }
    }

    fun updateData(newData: List<DocumentResponse>) {
        searchList.clear()
        searchList.addAll(newData)
        notifyDataSetChanged()
    }


}