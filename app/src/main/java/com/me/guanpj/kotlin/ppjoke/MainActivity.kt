package com.me.guanpj.kotlin.ppjoke

import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.me.guanpj.kotlin.ppjoke.view.AppBottomNavBar
import com.me.guanpj.kotlin.ppjoke.utils.NavGraphBuilder
import com.me.guanpj.libcommon.util.StatusBar

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        StatusBar.fixSystemBar(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: AppBottomNavBar = findViewById(R.id.nav_view)

        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!
        navController = NavHostFragment.findNavController(fragment)

        NavGraphBuilder.build(navController, this, fragment.id)
        navView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navController.navigate(item.itemId)
        return !TextUtils.isEmpty(item.title)
    }
}
