package com.example.searchresources.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.searchresources.ui.bookmark.BookmarkFragment
import com.example.searchresources.ui.searchList.SearchListFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchListFragment()
            1 -> BookmarkFragment()

            // TODO: UnknownFragement() 예외 처리 추가
            else -> throw RuntimeException("현재 Fragment는 2개 입니다.")
        }
    }
}