package com.me.guanpj.kotlin.ppjoke.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.me.guanpj.kotlin.ppjoke.databinding.LayoutPlayerViewBinding
import com.me.guanpj.libcommon.util.PixelUtils


class ListPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    var mCategory: String? = null
    var mVideoUrl: String? = null
    var isPlaying = false
    var mWidthPx = 0
    var mHeightPx = 0

    private var binding: LayoutPlayerViewBinding =
        LayoutPlayerViewBinding.inflate(LayoutInflater.from(context), this)

    fun bindData(
        category: String,
        widthPx: Int,
        heightPx: Int,
        coverUrl: String,
        videoUrl: String
    ) {
        mCategory = category
        mVideoUrl = videoUrl
        mWidthPx = widthPx
        mHeightPx = heightPx
        binding.cover.setImageUrl(coverUrl)

        //如果该视频的宽度小于高度,则高斯模糊背景图显示出来
        if (widthPx < heightPx) {
            PPImageView.setBlurImageUrl(binding.blurBackground, coverUrl, 10)
            binding.blurBackground.visibility = View.VISIBLE
        } else {
            binding.blurBackground.visibility = View.INVISIBLE
        }
        setSize(widthPx, heightPx)
    }

    private fun setSize(widthPx: Int, heightPx: Int) {
        //这里主要是做视频宽大与高,或者高大于宽时  视频的等比缩放
        val maxWidth: Int = PixelUtils.getScreenWidth()
        var layoutHeight = 0
        val coverWidth: Int
        val coverHeight: Int
        if (widthPx >= heightPx) {
            coverWidth = maxWidth
            coverHeight = (heightPx / (widthPx * 1.0f / maxWidth)).toInt()
            layoutHeight = coverHeight
        } else {
            coverHeight = maxWidth
            layoutHeight = coverHeight
            coverWidth = (widthPx / (heightPx * 1.0f / maxWidth)).toInt()
        }

        val params = layoutParams
        params.width = maxWidth
        params.height = layoutHeight
        layoutParams = params

        val blurParams = binding.blurBackground.layoutParams
        blurParams.width = maxWidth
        blurParams.height = layoutHeight
        binding.blurBackground.layoutParams = blurParams

        val coverParams = binding.cover.layoutParams as LayoutParams
        coverParams.width = coverWidth
        coverParams.height = coverHeight
        coverParams.gravity = Gravity.CENTER
        binding.cover.layoutParams = coverParams

        val playBtnParams =
            binding.playBtn.layoutParams as LayoutParams
        playBtnParams.gravity = Gravity.CENTER
        binding.playBtn.layoutParams = playBtnParams
    }
}