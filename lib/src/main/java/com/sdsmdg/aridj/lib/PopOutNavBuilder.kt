package com.sdsmdg.aridj.lib

import android.app.Activity
import android.graphics.Color
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class PopOutNavBuilder(private val activity: Activity, private val toolbar: Toolbar) {

    private var menuView: View ?= null

    fun build() {
        val contentView = activity.findViewById(android.R.id.content) as ViewGroup
        val rootView = contentView.getChildAt(0)
        contentView.removeAllViews()
        val navDrawer = PopOutNavLayout(activity, rootView)

        menuView = View(activity)
        menuView?.setBackgroundColor(Color.GREEN)

        val tempView = TextView(activity)
        tempView.text = "Hello"
        navDrawer.addView(menuView)

        addDrawer(navDrawer, menuView!!)
        navDrawer.addView(tempView)
        navDrawer.addView(rootView)
        contentView.addView(navDrawer)
    }

    private fun addDrawer(navDrawer: PopOutNavLayout, menu: View) {
        val drawerLayout = PopOutNavDrawerLayout(activity, navDrawer)

        val toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                R.string.drawer_open,
                R.string.drawer_close)
        toggle.syncState()

        navDrawer.setMenu(menu)
        navDrawer.setDrawerListener(toggle)
    }
}