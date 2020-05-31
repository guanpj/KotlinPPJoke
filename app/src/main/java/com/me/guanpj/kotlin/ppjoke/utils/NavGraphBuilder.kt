package com.me.guanpj.kotlin.ppjoke.utils

import android.content.ComponentName
import androidx.fragment.app.FragmentActivity
import androidx.navigation.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.FragmentNavigator
import com.me.guanpj.kotlin.ppjoke.FixedFragmentNavigator
import com.me.guanpj.libcommon.AppGlobals

object NavGraphBuilder {
    fun build(controller: NavController, fragmentActivity: FragmentActivity, containerId: Int) {
        val navigatorProvider = controller.navigatorProvider

        //val fragmentNavigator = navigatorProvider.getNavigator(FragmentNavigator::class.java)

        val fragmentNavigator = FixedFragmentNavigator(fragmentActivity, fragmentActivity.supportFragmentManager, containerId)
        navigatorProvider.addNavigator(fragmentNavigator)

        val activityNavigator = navigatorProvider.getNavigator(ActivityNavigator::class.java)
        val navGraph = NavGraph(NavGraphNavigator(navigatorProvider))

        val destConfig = AppConfig.destConfig
        destConfig.values.forEach {
            if (it.isFragment) {
                val destination = fragmentNavigator.createDestination()
                destination.className = it.className
                destination.id = it.id
                destination.addDeepLink(it.pageUrl)

                navGraph.addDestination(destination)
            } else {
                val destination = activityNavigator.createDestination()
                destination.setComponentName(ComponentName(AppGlobals.application.packageName, it.className))
                destination.id = it.id
                destination.addDeepLink(it.pageUrl)

                navGraph.addDestination(destination)
            }

            if (it.asStarter) {
                navGraph.startDestination = it.id
            }
        }

        controller.graph = navGraph
    }
}