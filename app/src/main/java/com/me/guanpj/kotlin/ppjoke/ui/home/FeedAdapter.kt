package com.me.guanpj.kotlin.ppjoke.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.me.guanpj.kotlin.ppjoke.databinding.LayoutFeedTypeImageBinding
import com.me.guanpj.kotlin.ppjoke.databinding.LayoutFeedTypeVideoBinding
import com.me.guanpj.kotlin.ppjoke.model.Feed

open class FeedAdapter constructor(val context: Context, val category: String) :
    PagedListAdapter<Feed, FeedAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<Feed>() {
            override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean =
                oldItem.id == newItem.id

            override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean = oldItem == newItem
        }
    ) {

    override fun getItemViewType(position: Int): Int = getItem(position)!!.itemType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding: ViewDataBinding =
            if (viewType == Feed.TYPE_IMAGE_TEXT) LayoutFeedTypeImageBinding.inflate(inflater)
            else LayoutFeedTypeVideoBinding.inflate(inflater)
        return ViewHolder(binding.root, binding, category)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bindData(it) }
    }

    class ViewHolder(
        val itemView: View,
        val binding: ViewDataBinding,
        val category: String
    ) : RecyclerView.ViewHolder(itemView) {
        fun bindData(item: Feed) {
            if (binding is LayoutFeedTypeImageBinding) {
                binding.feed = item
                binding.feedImage.bindData(item.width, item.height, 16, item.cover)
            } else if (binding is LayoutFeedTypeVideoBinding) {
                binding.feed = item
                binding.listPlayerView.bindData(category, item.width, item.height, item.cover, item.url)
            }
        }
    }
}