package com.example.searchresources.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.searchresources.databinding.ActivityMainBinding
import com.example.searchresources.ui.searchList.SearchListItem
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initTab()
    }

    private fun initTab() = with(binding) {
        val names = listOf("이미지 검색", "내 보관함")

        vpMain.adapter = ViewPagerAdapter(this@MainActivity)

        TabLayoutMediator(tlMain, vpMain) { tab, position ->
            tab.text = names[position]
        }.attach()
    }

    companion object {
        val selectedSearchImageList = mutableListOf<SearchListItem>()
    }
}