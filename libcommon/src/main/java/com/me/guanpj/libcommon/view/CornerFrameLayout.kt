package com.me.guanpj.libcommon.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import com.me.guanpj.libcommon.view.ViewHelper.setViewOutline

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CornerFrameLayout : FrameLayout {

    constructor(context: Context):
            this(context, null, 0)

    constructor(context: Context, attributeSet: AttributeSet?):
            this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int):
            this(context, attributeSet, defStyleAttr, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int):
            super(context, attributeSet, defStyleAttr, defStyleRes) {
        ViewHelper.setViewOutline(this, attributeSet, defStyleAttr, defStyleRes);
    }

    fun setViewOutline(radius: Int, radiusSide: Int) {
        setViewOutline(this, radius, radiusSide)
    }
}