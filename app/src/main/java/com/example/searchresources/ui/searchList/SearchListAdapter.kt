package com.example.searchresources.ui.searchList

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchresources.R
import com.example.searchresources.databinding.ItemSearchimageBinding

// ref: https://developer.android.com/reference/androidx/recyclerview/widget/DiffUtil.ItemCallback
class SearchListAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<SearchListItem, SearchListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<SearchListItem>() {
        // 현재 리스트에 노출하고 있는 아이템과 새로운 아이템이 서로 같은지 비교
        // 권장 사항 : Item의 파라미터에 고유한 ID 값이 있는 경우, 이 메서드는 ID를 기준으로 동일성을 반환 해야함
        override fun areItemsTheSame(oldItem: SearchListItem, newItem: SearchListItem): Boolean {
            // TODO: 마땅한 기본 키가 없음
            return oldItem == newItem
        }

        // areContentsTheSame : 현재 리스트에 노출하고 있는 아이템과 새로운 아이템의 equals를 비교한다.
        // 역할 : 한 Item의 내용이 변경되었는지 감지 (ex 좋아요 state 감지)
        // 호출 경로 : areItemsTheSame 메서드가 true를 반환하는 경우
        override fun areContentsTheSame(oldItem: SearchListItem, newItem: SearchListItem): Boolean {
            return (oldItem.marked == newItem.marked) && (oldItem == newItem)
        }
    }
) {
    inner class ViewHolder(
        val binding: ItemSearchimageBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onClick(adapterPosition)
            }
        }

        fun onBind(searchListItem: SearchListItem) = with(binding) {
            Glide.with(binding.root)
                .load(searchListItem.thumbnail)
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivThumbnailImage)

            tvDisplaySitename.text = searchListItem.site
            tvDatetime.text = searchListItem.datetime

            ivMark.visibility = if (searchListItem.marked) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchimageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}