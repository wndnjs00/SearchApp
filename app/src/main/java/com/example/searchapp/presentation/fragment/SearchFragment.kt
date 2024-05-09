package com.example.searchapp.presentation.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.searchapp.data.model.DocumentResponse
import com.example.searchapp.presentation.viewmodel.SearchViewModel
import com.example.searchapp.presentation.viewmodel.SearchViewModelFactory
import com.example.searchapp.databinding.FragmentSearchBinding
import com.google.gson.Gson


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchAdapter : SearchAdapter by lazy {
        SearchAdapter(searchList = ArrayList()){ search, postition ->
            // 클릭 시
            adapterClick(search,postition)
        }
    }

    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory()
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
            Log.d("apple", it.toString())         // 전체 데이터

            // 데이터 업데이트
//            this.searchAdapter.submitList(ArrayList(it))
            this.searchAdapter.submitList(it as ArrayList)
            this.searchAdapter.notifyDataSetChanged()
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

        getPrefsStorageItems(documentResponse)

    }


    // documentResponse 객체 아이템을 Json 문자열로 변환한 후 SharedPreferences로 저장
    private fun getPrefsStorageItems(documentResponse: DocumentResponse){
        val pref = activity?.getSharedPreferences("favorite_prefs", Context.MODE_PRIVATE)
        val edit = pref?.edit()
        val jsonString = Gson().toJson(documentResponse)
        Log.d("jsonString",jsonString)  //데이터들 값이 저장됨

        edit?.putString("STORAGE_ITEMS", jsonString)
        edit?.apply()
    }


}


