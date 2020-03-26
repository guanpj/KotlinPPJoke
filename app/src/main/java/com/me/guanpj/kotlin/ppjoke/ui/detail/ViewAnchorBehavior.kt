package com.me.guanpj.kotlin.ppjoke.ui.detail

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.me.guanpj.kotlin.ppjoke.R
import com.me.guanpj.libcommon.util.PixelUtils


class ViewAnchorBehavior() : CoordinatorLayout.Behavior<View>() {
    private var extraUsed = 0
    private var anchorId = 0

    constructor(context: Context, attributeSet: AttributeSet?) : this() {
        val array: TypedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.view_anchor_behavior, 0, 0)
        anchorId = array.getResourceId(R.styleable.view_anchor_behavior_anchorId, 0)
        array.recycle()
        extraUsed = PixelUtils.dp2px(48)
    }

    constructor(anchorId: Int) : this() {
        this.anchorId = anchorId
        extraUsed = PixelUtils.dp2px(48)
    }

    override fun layoutDependsOn(parent: CoordinatorLayout,
                                 child: View, dependency: View): Boolean = anchorId == dependency.id

    //CoordinatorLayout 在测量每一个view的时候 会调用该方法，如果返回true
    //CoordinatorLayout 就不会再次测量child了。会使用咱们给的测量的值 去摆放view的位置
    override fun onMeasureChild(parent: CoordinatorLayout, child: View, parentWidthMeasureSpec: Int,
        widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int): Boolean {
        var heightUsed = heightUsed
        val anchorView = parent.findViewById<View>(anchorId) ?: return false
        val layoutParams =
            child.layoutParams as CoordinatorLayout.LayoutParams
        val topMargin = layoutParams.topMargin
        val bottom = anchorView.bottom

        //再测量子View时，需要告诉CoordinatorLayout。垂直方向上 已经有多少空间被占用了
        //如果heightUsed给0，那么评论列表这个view它测量出来的高度 将会大于它实际的高度。以至于会被底部互动区域给遮挡
        heightUsed = bottom + topMargin + extraUsed
        parent.onMeasureChild(child, parentWidthMeasureSpec, 0, parentHeightMeasureSpec, heightUsed)
        return true
    }

    //当CoordinatorLayout 在摆放 每一个子View 的时候 会调该方法
    //如果return true.CoordinatorLayout 就不会再次摆放这个view的位置了
    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        val anchorView = parent.findViewById<View>(anchorId) ?: return false
        val params =
            child.layoutParams as CoordinatorLayout.LayoutParams
        val topMargin = params.topMargin
        val bottom = anchorView.bottom
        parent.onLayoutChild(child, layoutDirection)
        //偏移量
        child.offsetTopAndBottom(bottom + topMargin)
        return true
    }
}