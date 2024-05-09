package com.example.searchresources.ui.searchList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.searchresources.data.model.SearchImageResponse
import com.example.searchresources.databinding.FragmentSearchListBinding
import com.example.searchresources.domain.model.toEntity
import com.example.searchresources.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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