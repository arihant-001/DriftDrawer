package com.sdsmdg.aridj.lib

import android.app.Activity
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.ViewGroup

class PopOutNavBuilder(private val activity: Activity, private val toolbar: Toolbar) {

    //    private var menuView: View ?= null
    private var menuIds: ArrayList<Int>

    init {
        menuIds = ArrayList()
    }

    fun withMenus(menus: ArrayList<Int>): PopOutNavBuilder {
        this.menuIds = menus

        return this
    }

    fun build() {
        val contentView = activity.findViewById(android.R.id.content) as ViewGroup
        val rootView = contentView.getChildAt(0)
        contentView.removeAllViews()
        val navDrawer = PopOutNavLayout(activity, rootView)

        navDrawer.addMenus(menuIds)

//        navDrawer.addView(menuView)

        addDrawer(navDrawer)
//        navDrawer.addView(tempView)
        navDrawer.addView(rootView)
        contentView.addView(navDrawer)
    }

    private fun addDrawer(navDrawer: PopOutNavLayout) {
        val drawerLayout = PopOutNavDrawerLayout(activity, navDrawer)

        val toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                R.string.drawer_open,
                R.string.drawer_close)
        toggle.syncState()

        navDrawer.setDrawerListener(toggle)
    }
}