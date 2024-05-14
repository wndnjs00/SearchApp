package com.example.searchapp.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.searchapp.data.model.DocumentResponse
import com.example.searchapp.databinding.FragmentStorageBinding
import com.example.searchapp.presentation.viewmodel.StorageViewModel


class StorageFragment : Fragment() {


    private var _binding : FragmentStorageBinding? = null
    private val binding get() = _binding!!

    private val storageAdapter : StorageAdapter by lazy {
        StorageAdapter(storageList = ArrayList()){ search, position ->
            adapterClick(search,position)
        }
    }

    private val storageViewModel by viewModels<StorageViewModel>{
        StorageViewModel.StorageViewModelFactory()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStorageBinding.inflate(inflater,container,false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        viewModel()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    // 어뎁터와 리사이클러뷰 연결
    private fun setRecyclerView(){

        with(binding.recyclerViewStorage){
            adapter = storageAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun viewModel(){

        // BookmarkFragment가 호출될때, 북마크된 아이템을 가져옴
        storageViewModel.getBookmarkedItems(context)

        // ViewModel을 observe해서 실시간 변경되는 데이터(북마크한 아이템 리스트)관찰
        storageViewModel.getStorageImageLiveData.observe(viewLifecycleOwner){

            storageAdapter.clearItem()
            storageAdapter.storageList = it as ArrayList<DocumentResponse>
            storageAdapter.notifyDataSetChanged()
        }
    }


    // 아이템 클릭시
    private fun adapterClick(documentResponse: DocumentResponse,position : Int){


        Log.d("position", position.toString())
        Toast.makeText(requireContext(), "클릭 $position", Toast.LENGTH_SHORT).show()

        // 클릭한 아이템 삭제
        storageViewModel.deleteItem(context, documentResponse, position)
    }

}