package com.example.searchresources.ui.searchList

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.searchresources.data.model.SearchImageResponse
import com.example.searchresources.databinding.FragmentSearchListBinding
import com.example.searchresources.domain.model.toEntity
import com.example.searchresources.network.RetrofitClient
import com.example.searchresources.ui.MainActivity.Companion.selectedSearchImageList
import com.example.searchresources.ui.SearchAdapter
import com.example.searchresources.ui.util.GridSpacingItemDecoration
import com.example.searchresources.ui.util.convertItems
import com.example.searchresources.ui.util.fromDpToPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class SearchListFragment : Fragment() {
    private var _binding: FragmentSearchListBinding? = null
    private val binding get() = _binding!!

    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter() { position ->
            clickListener(position)
        }
    }

    private lateinit var searchListItems: MutableList<SearchListItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchListBinding.inflate(inflater, container, false)
        loadData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvSearchItem.adapter = searchAdapter
            rvSearchItem.addItemDecoration(
                GridSpacingItemDecoration(2, 4f.fromDpToPx())
            )

            btnSearch.setOnClickListener {
                val keyword = etKeyword.text.toString()

                CoroutineScope(Dispatchers.IO).launch {
                    val data = RetrofitClient.search.getSearchImage(keyword)

                    Log.d("API TEST", data.toString())

                    withContext(Dispatchers.Main) {
                        searchListItems = convertItems(data).toMutableList()
                        searchAdapter.submitList(searchListItems)
                    }
                }

                saveData()

                // 키보드 내리기
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(
                    requireActivity().window.decorView.applicationWindowToken,
                    0
                )
            }
        }
    }

    fun clickListener(position: Int) {
        searchListItems[position] = searchListItems[position].let {
            it.copy(
                marked = !it.marked
            )
        }

        if (searchListItems[position].marked) {
            selectedSearchImageList.add(searchListItems[position])
        } else {
            selectedSearchImageList.remove(searchListItems[position])
        }

        searchAdapter.notifyItemChanged(position)

        // Log.d("Test", searchListItems[position].marked.toString())
        // Log.d("Test", selectedSearchImageList.toString())
    }

    private fun saveData() {
        val pref = activity.let {
            it!!.getSharedPreferences("data", Context.MODE_PRIVATE) // 0
        }

        val edit = pref.edit()

        edit.putString("name", binding.etKeyword.text.toString())
        edit.apply()
    }

    private fun loadData() {
        val pref = activity.let {
            it!!.getSharedPreferences("data", 0)
        }

        val name = pref.getString("name", "")!!
        binding.etKeyword.setText(name)
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        private var INSTANCE: SearchListFragment? = null

        fun newInstance(): SearchListFragment {
            return synchronized(SearchListFragment::class.java) {
                val instance = INSTANCE ?: SearchListFragment()

                if (INSTANCE == null) {
                    INSTANCE = instance
                }

                instance
            }
        }
    }
}