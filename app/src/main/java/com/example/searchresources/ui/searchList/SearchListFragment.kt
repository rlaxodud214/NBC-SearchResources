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
import com.example.searchresources.ui.util.GridSpacingItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


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
        loadData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvSearchItem.adapter = searchListAdapter
            rvSearchItem.addItemDecoration(
                GridSpacingItemDecoration(2, 4f.fromDpToPx())
            )

            btnSearch.setOnClickListener {
                val keyword = etKeyword.text.toString()

                CoroutineScope(Dispatchers.IO).launch {
                    val data = RetrofitClient.search.getSearchImage(keyword)

                    Log.d("API TEST", data.toString())

                    withContext(Dispatchers.Main) {
                        searchListAdapter.submitList(convertItems(data))
                    }
                }

                saveData()

                // 키보드 내리기
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireActivity().window.decorView.applicationWindowToken, 0)
            }
        }
    }

    private fun Float.fromDpToPx(): Int =
        (this * Resources.getSystem().displayMetrics.density).toInt()

    private fun convertItems(data : SearchImageResponse): List<SearchListItem> {
        val documents = data.toEntity().documents ?: return emptyList()

        return documents.map {
            SearchListItem(
                thumbnail = it.thumbnail_url,
                site = it.display_sitename,
                datetime = convertDatetime(it.datetime!!),
            )
        }!!
    }

    private fun convertDatetime(datetime: String): String {
        // 원래 문자열을 ZonedDateTime으로 파싱
        val zonedDateTime = ZonedDateTime.parse(datetime)

        // LocalDateTime으로 변환
        val localDateTime = zonedDateTime.toLocalDateTime()

        // 원하는 출력 포맷 정의
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        // 포맷 적용 후 문자열로 변환
        val formattedString = localDateTime.format(formatter)

        // 결과 출력
        Log.d("convert", formattedString)
        return formattedString
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
}