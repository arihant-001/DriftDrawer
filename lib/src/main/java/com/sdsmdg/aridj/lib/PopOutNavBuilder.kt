package com.sdsmdg.aridj.lib

import android.app.Activity
import android.graphics.Color
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import android.view.View

class PopOutNavBuilder(private val activity: Activity, private val toolbar: Toolbar) {

    private var menuView: View ?= null

    fun build() {
        val contentView = activity.findViewById(android.R.id.content) as ViewGroup
        val rootView = contentView.getChildAt(0)
        contentView.removeAllViews()
        val navDrawer = PopOutNavLayout(activity)

        addDrawer(navDrawer)
        menuView = View(activity)
        menuView?.setBackgroundColor(Color.GREEN)

        navDrawer.addView(menuView)
        navDrawer.addView(rootView)
        contentView.addView(navDrawer)
    }

    private fun addDrawer(navDrawer: PopOutNavLayout) {
        val drawerLayout = PopOutNavDrawerLayout(activity)
        drawerLayout.setDrawer(navDrawer)

        val toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                R.string.drawer_open,
                R.string.drawer_close)
        toggle.syncState()
    }
}