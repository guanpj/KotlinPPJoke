package com.me.guanpj.libcommon.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.annotation.RequiresApi


class WindowInsetsFrameLayout : FrameLayout {
    constructor(context: Context):
            this(context, null, 0)

    constructor(context: Context, attributeSet: AttributeSet?):
            this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int):
            super(context, attributeSet, defStyleAttr)

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun addView(child: View?) {
        super.addView(child)
        requestApplyInsets()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun dispatchApplyWindowInsets(insets: WindowInsets): WindowInsets {
        var windowInsets = super.dispatchApplyWindowInsets(insets)
        if (!insets.isConsumed) {
            val childCount = childCount
            for (i in 0 until childCount) {
                windowInsets = getChildAt(i).dispatchApplyWindowInsets(insets)
            }
        }
        return windowInsets
    }
}