package com.me.guanpj.kotlin.ppjoke.ui.home

import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.me.guanpj.kotlin.ppjoke.model.Feed
import com.me.guanpj.kotlin.ppjoke.ui.AbsListFragment
import com.me.guanpj.libnavannotation.FragmentDestination
import com.scwang.smartrefresh.layout.api.RefreshLayout

@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
class HomeFragment : AbsListFragment<Feed, HomeViewModel>() {

    var feedType = if (arguments == null) "all" else arguments!!.getString("feedType")

    override fun onLoadMore(refreshLayout: RefreshLayout) {
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
    }

    override fun createAdapter(): PagedListAdapter<Feed, RecyclerView.ViewHolder>
            = FeedAdapter(context!!, feedType!!) as PagedListAdapter<Feed, RecyclerView.ViewHolder>
}
