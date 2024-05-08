package com.example.searchapp.presentation.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.searchapp.data.Constants
import com.example.searchapp.data.model.DocumentResponse
import com.example.searchapp.data.model.SearchResponse
import com.example.searchapp.presentation.viewmodel.SearchViewModel
import com.example.searchapp.presentation.viewmodel.SearchViewModelFactory
import com.example.searchapp.databinding.FragmentSearchBinding
import com.example.searchapp.presentation.viewmodel.StorageViewModel
import com.example.searchapp.presentation.viewmodel.StorageViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchAdapter : SearchAdapter by lazy {
        SearchAdapter(searchList = ArrayList()){ search, postition ->
            // 클릭 시
            adapterClick(search,postition)
        }
    }

    private val storageAdapter : StorageAdapter by lazy {
        StorageAdapter(searchList = ArrayList())
    }

    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory()
    }

    private val storageViewModel by viewModels<StorageViewModel> {
        StorageViewModelFactory()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        searchBtn()
        removeText()

        // ViewModel을 observe해서 실시간 변경되는 데이터관찰
        searchViewModel.getSearchImageLiveData.observe(viewLifecycleOwner){
            Log.d("debugSearchData", it.toString())         // 전체 데이터

            // 데이터 업데이트
            this.searchAdapter.submitList(ArrayList(it))
            this.searchAdapter.notifyDataSetChanged()
        }

        storageViewModel.getStorageLiveData.observe(viewLifecycleOwner){
            Log.d("storageData", it.toString())
            this.storageAdapter.submitList(ArrayList(it))
            this.storageAdapter.notifyDataSetChanged()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    // 어뎁터와 리사이클러뷰 연결
    private fun setRecyclerView(){

        with(binding.recyclerViewSearch){
            adapter = searchAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }


    private fun searchBtn(){

        binding.searchBtn.setOnClickListener {
            saveSearchData()
            hideKeyboard()

            val searchText = binding.searchViewEt.text.toString()
            if (searchText.isNotEmpty()) {
                // viewModel에 작성한 텍스트값 전달
                searchViewModel.getSearchImageList(searchText)
            }
        }

        loadSearchData()
    }


    private fun removeText(){
        with(binding){
            searchClose.setOnClickListener {
                searchViewEt.setText("")
            }
        }
    }


    private fun saveSearchData(){
        // fragment에서는 sharedPreferences 쓸려면 activtiy를 참조해서 써줘야함
        val sharedPreferences = activity?.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val edit = sharedPreferences?.edit()
        val searchData = binding.searchViewEt.text.toString()

        edit?.putString("searchData", searchData)
        edit?.apply()
    }

    private fun loadSearchData(){
        val sharedPreferences = activity?.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        // getString을 이용해 데이터를 꺼내서 사용
        // default값 설정해줘야함 -> 데이터가 null일때(존재하지 않을떄의 값)
        val searchData = sharedPreferences?.getString("searchData", null)

        binding.searchViewEt.setText(searchData)
    }




    // 키보드 숨기는 함수
    @SuppressLint("ServiceCast")
    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    // 리사이클러뷰 아이템 클릭 이벤트
    private fun adapterClick(documentResponse: DocumentResponse, position : Int){

        // 선택한 아이템의 postion값
        Toast.makeText(requireContext(), position.toString(), Toast.LENGTH_SHORT).show()
        Log.d("click_position", position.toString())

    }









//    // 저장돠어있는 아이템 리스트 목록을 가져오는 함수
//    private fun getPrefsStorageItems() : List<DocumentResponse>{
//        val sharedPreferences = activity?.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
//        val joinString = sharedPreferences?.getString("get_items", "")
//        return if (joinString!!.isNotEmpty()){
//            emptyList()
//        }else{
//            Gson().fromJson(joinString, object : TypeToken<List<DocumentResponse>>() {}.type)
//        }
//    }
//
//
//    // 이미지 클릭하면 저장소에 추가하기위한 함수
//    private fun saveStorageItem(documentResponse: DocumentResponse){
//        val bookmarkItems = getPrefsStorageItems().toMutableList()
//        val findItem = bookmarkItems.find { it == documentResponse }
//
//        if (findItem == null){
//            bookmarkItems.add(documentResponse)
//            savePrefsStorageItems(bookmarkItems)
//        }
//    }
//
//     // SearchModel 객체 아이템을 Json 문자열로 변환한 후 저장
//    private fun savePrefsStorageItems(items: List<DocumentResponse>) {
//        val jsonString = Gson().toJson(items)
//        val sharedPreferences = activity?.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
//        sharedPreferences?.edit()?.putString("get_items", jsonString)?.apply()
//    }
//
//     suspend fun getStorageItems(): List<DocumentResponse> {
//        return getPrefsStorageItems()
//    }





}


