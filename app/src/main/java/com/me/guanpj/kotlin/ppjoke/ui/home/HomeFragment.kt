package com.me.guanpj.kotlin.ppjoke.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.me.guanpj.kotlin.ppjoke.R
import com.me.guanpj.kotlin.ppjoke.model.Feed
import com.me.guanpj.kotlin.ppjoke.ui.AbsListFragment
import com.me.guanpj.kotlin.ppjoke.utils.AppConfig
import com.me.guanpj.libnavannotation.FragmentDestination
import com.scwang.smartrefresh.layout.api.RefreshLayout

@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
class HomeFragment : AbsListFragment<Feed, HomeViewModel>() {

    private lateinit var homeViewModel: HomeViewModel
    var feedType = if (arguments == null) "all" else arguments!!.getString("feedType")

    override fun onLoadMore(refreshLayout: RefreshLayout) {
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
    }

    override fun createAdapter(): PagedListAdapter<Feed, RecyclerView.ViewHolder>
            = FeedAdapter(context!!, feedType!!) as PagedListAdapter<Feed, RecyclerView.ViewHolder>
}
