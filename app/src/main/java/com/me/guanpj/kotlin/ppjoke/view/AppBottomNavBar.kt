package com.me.guanpj.kotlin.ppjoke.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.me.guanpj.kotlin.ppjoke.R
import com.me.guanpj.kotlin.ppjoke.utils.AppConfig
import com.me.guanpj.kotlin.ppjoke.utils.ScreenUtils

class AppBottomNavBar : BottomNavigationView {

    private val icons = intArrayOf(R.drawable.icon_tab_home, R.drawable.icon_tab_sofa, R.drawable.icon_tab_publish, R.drawable.icon_tab_find, R.drawable.icon_tab_mine)
    private val states = arrayOf(intArrayOf(android.R.attr.state_selected), intArrayOf())

    constructor(context: Context):
            this(context, null, 0)

    constructor(context: Context, attributeSet: AttributeSet?):
            this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int):
            super(context, attributeSet, defStyleAttr)

    init {
        initTabs()
    }

    @SuppressLint("RestrictedApi")
    private fun initTabs() {
        val bottomBarConfig = AppConfig.bottomNavBarConfig

        val colors = intArrayOf(
            Color.parseColor(bottomBarConfig.activeColor),
            Color.parseColor(bottomBarConfig.inActiveColor)
        )
        val stateList = ColorStateList(states, colors)
        itemIconTintList = stateList
        itemTextColor = stateList
        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        selectedItemId = bottomBarConfig.selectedTab

        val enableTabs = bottomBarConfig.tabs.filter {
            it.enable && getItemId(it.pageUrl) > 0
        }

        enableTabs.forEach {
            val menuItem = menu.add(0, getItemId(it.pageUrl), it.index, it.title)
            menuItem.setIcon(icons[it.index])
        }

        enableTabs.forEach {
            val iconSize: Int = ScreenUtils.dp2Px(context, it.size)
            val menuView = getChildAt(0) as BottomNavigationMenuView
            val itemView = menuView.getChildAt(it.index) as BottomNavigationItemView
            itemView.setIconSize(iconSize)
            if (TextUtils.isEmpty(it.title)) {
                val tintColor =
                    if (TextUtils.isEmpty(it.tintColor)) Color.parseColor("#ff678f") else Color.parseColor(
                        it.tintColor
                    )
                itemView.setIconTintList(ColorStateList.valueOf(tintColor))
                itemView.setShifting(false)
            }
        }
    }

    private fun getItemId(pageUrl: String): Int {
        val destination = AppConfig.destConfig[pageUrl] ?: return -1
        return destination.id
    }
}