package com.me.guanpj.kotlin.ppjoke.view

import android.R.attr
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.me.guanpj.libcommon.util.PixelUtils
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


class PPImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr){

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl", "isCircle")
        fun setImageUrl(imageView: PPImageView, imageUrl: String?, isCircle: Boolean?) {
            setImageUrl(imageView, imageUrl, isCircle, 0)
        }

        @JvmStatic
        @SuppressLint("CheckResult")
        @BindingAdapter("imageUrl", "isCircle", "radius")
        fun setImageUrl(imageView: PPImageView, imageUrl: String?, isCircle: Boolean?, radius: Int?) {
            val builder = Glide.with(imageView).load(imageUrl)
            isCircle?.let { circle ->
                if (circle) {
                    builder.transform(CircleCrop())
                } else {
                    radius?.let {
                        if (it > 0) {
                            builder.transform(RoundedCornersTransformation(PixelUtils.dp2px(radius), 0))
                        }
                    }
                }
            }
            /*if (isCircle) {
                builder.transform(CircleCrop())
            } else if (radius > 0) {
                builder.transform(RoundedCornersTransformation(PixelUtils.dp2px(radius), 0))
            }*/
            val layoutParams = imageView.layoutParams
            if (layoutParams != null && layoutParams.width > 0 && layoutParams.height > 0) {
                builder.override(layoutParams.width, layoutParams.height)
            }
            builder.into(imageView)
        }

        @JvmStatic
        @BindingAdapter("blurImageUrl", "radius")
        fun setBlurImageUrl(imageView: PPImageView, blurImageUrl: String?, radius: Int?) {
            if (radius != null) {
                Glide.with(imageView).load(blurImageUrl).override(radius)
                    .transform(BlurTransformation())
                    .dontAnimate()
                    .into(object : SimpleTarget<Drawable?>() {
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable?>?
                        ) {
                            imageView.background = resource
                        }
                    })
            }
        }
    }

    fun setImageUrl(imageUrl: String) {
        setImageUrl(this, imageUrl, false)
    }

    fun bindData(widthPx: Int, heightPx: Int, marginLeft: Int, imageUrl: String?) {
        bindData(
            widthPx, heightPx, marginLeft,
            PixelUtils.getScreenWidth(), PixelUtils.getScreenWidth(), imageUrl
        )
    }

    fun bindData(widthPx: Int, heightPx: Int, marginLeft: Int, maxWidth: Int,
                    maxHeight: Int, imageUrl: String?) {
        if (TextUtils.isEmpty(imageUrl)) {
            visibility = View.GONE
            return
        } else {
            visibility = View.VISIBLE
        }
        if (widthPx <= 0 || heightPx <= 0) {
            Glide.with(this).load(imageUrl)
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, ransition: Transition<in Drawable>?) {
                        val height = resource.intrinsicHeight
                        val width = resource.intrinsicWidth
                        setSize(width, height, marginLeft, maxWidth, maxHeight)
                        setImageDrawable(resource)
                    }
                })
            return
        }
        setSize(widthPx, heightPx, marginLeft, maxWidth, maxHeight)
        setImageUrl(this, imageUrl!!, false)
    }

    private fun setSize(width: Int, height: Int, marginLeft: Int,
                    maxWidth: Int, maxHeight: Int) {
        val finalWidth: Int
        val finalHeight: Int
        if (width > height) {
            finalWidth = maxWidth
            finalHeight = (height / (width * 1.0f / finalWidth)).toInt()
        } else {
            finalHeight = maxHeight
            finalWidth = (width / (height * 1.0f / finalHeight)).toInt()
        }
        val params = layoutParams
        params.width = finalWidth
        params.height = finalHeight
        if (params is FrameLayout.LayoutParams) {
            params.leftMargin =
                if (height > width) PixelUtils.dp2px(marginLeft) else 0
        } else if (params is LinearLayout.LayoutParams) {
            params.leftMargin =
                if (height > width) PixelUtils.dp2px(marginLeft) else 0
        }
        layoutParams = params
    }
}