package com.me.guanpj.libcommon.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.me.guanpj.libcommon.databinding.LayoutEmptyViewBinding


class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var binding: LayoutEmptyViewBinding

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        binding = LayoutEmptyViewBinding.inflate(LayoutInflater.from(context), this, true)
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