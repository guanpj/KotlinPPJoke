package com.me.guanpj.libcommon.view

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import com.me.guanpj.libcommon.R
import com.me.guanpj.libcommon.databinding.LayoutEmptyViewBinding


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class EmptyView : LinearLayout {

    private lateinit var binding: LayoutEmptyViewBinding

    constructor(context: Context):
            this(context, null, 0)

    constructor(context: Context, attributeSet: AttributeSet?):
            this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int):
            this(context, attributeSet, defStyleAttr, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int):
            super(context, attributeSet, defStyleAttr, defStyleRes) {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        binding = LayoutEmptyViewBinding.inflate(LayoutInflater.from(context))
    }

    fun setEmptyIcon(@DrawableRes iconRes: Int) = binding.emptyIcon.setImageResource(iconRes)

    fun setTitle(text: String?) {
        if (TextUtils.isEmpty(text)) {
            binding.emptyText.visibility = View.GONE
        } else {
            binding.emptyText.text = text
            binding.emptyText.visibility = View.VISIBLE
        }
    }

    fun setButton(text: String?, listener: OnClickListener?) {
        if (TextUtils.isEmpty(text)) {
            binding.emptyAction.visibility = View.GONE
        } else {
            binding.emptyAction.text = text
            binding.emptyAction.visibility = View.VISIBLE
            binding.emptyAction.setOnClickListener(listener)
        }
    }
}