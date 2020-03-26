package com.me.guanpj.libcommon.view

import android.annotation.TargetApi
import android.view.View
import android.content.res.TypedArray
import android.graphics.Outline
import android.os.Build
import android.util.AttributeSet
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi
import com.me.guanpj.libcommon.R


object ViewHelper {
    const val RADIUS_ALL = 0
    const val RADIUS_LEFT = 1
    const val RADIUS_TOP = 2
    const val RADIUS_RIGHT = 3
    const val RADIUS_BOTTOM = 4

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setViewOutline(view: View, attributes: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val array: TypedArray = view.getContext().obtainStyledAttributes(
            attributes,
            R.styleable.viewOutLineStrategy,
            defStyleAttr,
            defStyleRes
        )
        val radius =
            array.getDimensionPixelSize(R.styleable.viewOutLineStrategy_clip_radius, 0)
        val hideSide = array.getInt(R.styleable.viewOutLineStrategy_clip_side, 0)
        array.recycle()
        setViewOutline(view, radius, hideSide)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setViewOutline(owner: View, radius: Int, radiusSide: Int) {
        owner.setOutlineProvider(@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        object : ViewOutlineProvider() {
            @TargetApi(21)
            override fun getOutline(view: View, outline: Outline) {
                val w: Int = view.getWidth()
                val h: Int = view.getHeight()
                if (w == 0 || h == 0) {
                    return
                }
                if (radiusSide != RADIUS_ALL) {
                    var left = 0
                    var top = 0
                    var right = w
                    var bottom = h
                    if (radiusSide == RADIUS_LEFT) {
                        right += radius
                    } else if (radiusSide == RADIUS_TOP) {
                        bottom += radius
                    } else if (radiusSide == RADIUS_RIGHT) {
                        left -= radius
                    } else if (radiusSide == RADIUS_BOTTOM) {
                        top -= radius
                    }
                    outline.setRoundRect(left, top, right, bottom, radius.toFloat())
                    return
                }
                val top = 0
                val left = 0
                if (radius <= 0) {
                    outline.setRect(left, top, w, h)
                } else {
                    outline.setRoundRect(left, top, w, h, radius.toFloat())
                }
            }
        })
        owner.setClipToOutline(radius > 0)
        owner.invalidate()
    }
}