package com.me.guanpj.kotlin.ppjoke.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.me.guanpj.kotlin.ppjoke.R
import com.me.guanpj.kotlin.ppjoke.databinding.LayoutRefreshViewBinding
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


abstract class AbsListFragment<T, M : AbsViewModel<T>> : Fragment(), OnLoadMoreListener, OnRefreshListener {
    lateinit var binding: LayoutRefreshViewBinding
    val adapter by lazy { createAdapter() }

    protected var mViewModel: M? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutRefreshViewBinding.inflate(inflater, container, false)

        binding.refreshLayout.setEnableRefresh(true)
        binding.refreshLayout.setEnableLoadMore(true)
        binding.refreshLayout.setOnRefreshListener(this)
        binding.refreshLayout.setOnLoadMoreListener(this)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val decoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        context?.let { context ->
            ContextCompat.getDrawable(context, R.drawable.list_divider)?.let {
                decoration.setDrawable(it)
            }
        }

        binding.recyclerView.addItemDecoration(decoration)

        genericViewModel()

        return binding.root
    }

    private fun genericViewModel() {
        //利用 子类传递的 泛型参数实例化出absViewModel 对象。
        val type: ParameterizedType = javaClass.genericSuperclass as ParameterizedType
        val arguments: Array<Type> = type.getActualTypeArguments()
        if (arguments.size > 1) {
            val argument: Type = arguments[1]
            val modelClz: Class<out AbsViewModel<*>> = (argument as Class<ViewModel>).asSubclass(AbsViewModel::class.java)

            mViewModel = ViewModelProviders.of(this).get(modelClz) as M

            mViewModel?.pageData?.observe(viewLifecycleOwner, Observer { pagedList -> submitList(pagedList) })

            mViewModel?.boundaryPageData?.observe(viewLifecycleOwner, Observer { hasData -> finishRefresh(hasData) })
        }
    }

    open fun submitList(result: PagedList<T>) {
        if (result.size > 0) {
            adapter.submitList(result)
        }
        finishRefresh(result.size > 0)
    }

    open fun finishRefresh(hasData: Boolean) {
        var hasData = hasData
        val currentList = adapter.currentList
        hasData = hasData || currentList != null && currentList.size > 0
        val state: RefreshState = binding.refreshLayout.getState()
        if (state.isFooter && state.isOpening) {
            binding.refreshLayout.finishLoadMore()
        } else if (state.isHeader && state.isOpening) {
            binding.refreshLayout.finishRefresh()
        }
        if (hasData) {
            binding.refreshLayout.setVisibility(View.GONE)
        } else {
            binding.refreshLayout.setVisibility(View.VISIBLE)
        }
    }


    abstract fun createAdapter(): PagedListAdapter<T, RecyclerView.ViewHolder>
}