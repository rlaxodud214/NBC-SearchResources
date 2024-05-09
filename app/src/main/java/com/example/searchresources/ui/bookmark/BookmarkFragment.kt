package com.example.searchresources.ui.bookmark

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.searchresources.databinding.FragmentBookmarkBinding
import com.example.searchresources.network.RetrofitClient
import com.example.searchresources.ui.MainActivity
import com.example.searchresources.ui.SearchAdapter
import com.example.searchresources.ui.searchList.SearchListItem
import com.example.searchresources.ui.util.GridSpacingItemDecoration
import com.example.searchresources.ui.util.convertItems
import com.example.searchresources.ui.util.fromDpToPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookmarkFragment : Fragment() {
    private var _binding: FragmentBookmarkBinding? = null
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
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvSearchItem.adapter = searchAdapter
            rvSearchItem.addItemDecoration(
                GridSpacingItemDecoration(2, 4f.fromDpToPx())
            )

            searchListItems = MainActivity.selectedSearchImageList
            searchAdapter.submitList(searchListItems)
        }
    }

    override fun onResume() {
        super.onResume()

        searchAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    fun clickListener(position: Int) {
        searchListItems[position] = searchListItems[position].let {
            it.copy(
                marked = !it.marked
            )
        }

        if (!searchListItems[position].marked) {
            MainActivity.selectedSearchImageList.add(searchListItems[position])
        } else {
            MainActivity.selectedSearchImageList.remove(searchListItems[position])
        }

        searchAdapter.notifyItemChanged(position)

        // Log.d("Test", searchListItems[position].marked.toString())
        // Log.d("Test", selectedSearchImageList.toString())
    }

    companion object {
        private var INSTANCE: BookmarkFragment? = null

        fun newInstance(): BookmarkFragment {
            return synchronized(BookmarkFragment::class.java) {
                val instance = INSTANCE ?: BookmarkFragment()

                if (INSTANCE == null) {
                    INSTANCE = instance
                }

                instance
            }
        }
    }
}