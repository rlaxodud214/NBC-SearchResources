package com.example.searchresources.ui.searchList

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.searchresources.data.model.SearchImageResponse
import com.example.searchresources.databinding.FragmentSearchListBinding
import com.example.searchresources.domain.model.toEntity
import com.example.searchresources.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchListFragment : Fragment() {
    private var _binding: FragmentSearchListBinding? = null
    private val binding get() = _binding!!

    private val searchListAdapter: SearchListAdapter by lazy {
        SearchListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvSearchItem.adapter = searchListAdapter

            btnSearch.setOnClickListener {
                val keyword = etKeyword.text.toString()

                CoroutineScope(Dispatchers.IO).launch {
                    val data = RetrofitClient.search.getSearchImage(keyword)

                    Log.d("API TEST", data.toString())

                    withContext(Dispatchers.Main) {
                        searchListAdapter.submitList(convertItems(data))
                    }
                }

                // 키보드 내리기
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireActivity().window.decorView.applicationWindowToken, 0)
            }
        }
    }

    private fun convertItems(data : SearchImageResponse): List<SearchListItem> {
        return data.toEntity().documents?.map {
            SearchListItem(
                thumbnail = it.thumbnail_url,
                site = it.display_sitename,
                datetime = it.datetime,
            )
        }!!
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}