package com.example.searchresources.ui.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// ref: https://velog.io/@dear_jjwim/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-RecyclerView-GridLayoutManager-%ED%95%AD%EC%83%81-%EC%9D%BC%EC%A0%95%ED%95%9C-%EC%95%84%EC%9D%B4%ED%85%9C-%EA%B0%84%EA%B2%A9-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0-Column-Space-ItemDecoration
class GridSpacingItemDecoration(
    private val spanCount: Int, // Grid의 column 수
    private val spacing: Int    // 간격
) : RecyclerView.ItemDecoration() {
    /*
        onDraw() : 항목을 배치하기 전에 호출됨
        onDrawOver() : 모든 항목이 배치된 후에 호출됨
        getItemOffsets : 각 항목을 배치할 때 호출됨
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position: Int = parent.getChildAdapterPosition(view)

        outRect.apply {
            if (position >= 0) {
                set(spacing, spacing, spacing, spacing)
            } else {
                set(0, 0, 0, 0)
            }
        }
    }
}