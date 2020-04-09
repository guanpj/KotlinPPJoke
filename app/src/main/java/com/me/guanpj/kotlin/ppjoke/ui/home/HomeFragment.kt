package com.me.guanpj.kotlin.ppjoke.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.me.guanpj.kotlin.ppjoke.model.Feed
import com.me.guanpj.kotlin.ppjoke.ui.AbsListFragment
import com.me.guanpj.kotlin.ppjoke.ui.MutablePageKeyedDataSource
import com.me.guanpj.libnavannotation.FragmentDestination
import com.scwang.smartrefresh.layout.api.RefreshLayout


@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
class HomeFragment : AbsListFragment<Feed, HomeViewModel>() {

    var feedType = if (arguments == null) "all" else arguments!!.getString("feedType")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel?.getCacheLiveData()?.observe(viewLifecycleOwner, object : Observer<PagedList<Feed>> {
            override fun onChanged(feeds: PagedList<Feed>) {
                submitList(feeds)
            }
        })
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        val currentList = adapter.currentList
        currentList?.isEmpty()?.let { dataEmpty ->
            if (dataEmpty) {
                finishRefresh(false)
                return
            } else {
                val feed = currentList[adapter.itemCount - 1]
                mViewModel?.loadAfter(feed!!.id, object : ItemKeyedDataSource.LoadCallback<Feed>() {
                    override fun onResult(data: MutableList<Feed>) {
                        if (data.isNotEmpty()) {
                            val dataSource = MutablePageKeyedDataSource<Feed>()
                            dataSource.data += currentList + data
                            val pageList = dataSource.buildNewPagedList(currentList.config)
                            pageList?.let {
                                submitList(it)
                            }
                        }
                    }
                })
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mViewModel?.dataSource?.invalidate()
    }

    override fun createAdapter(): PagedListAdapter<Feed, RecyclerView.ViewHolder>
            = FeedAdapter(context!!, feedType!!) as PagedListAdapter<Feed, RecyclerView.ViewHolder>
}
