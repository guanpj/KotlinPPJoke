package com.me.guanpj.libcommon.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.me.guanpj.libcommon.view.ViewHelper.setViewOutline

class CornerLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        ViewHelper.setViewOutline(this, attrs, defStyleAttr, defStyleRes);
    }
}