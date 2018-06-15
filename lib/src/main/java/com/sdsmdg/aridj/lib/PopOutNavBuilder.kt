package com.sdsmdg.aridj.lib

import android.app.Activity
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import com.sdsmdg.aridj.lib.transformations.CombineTransformation
import com.sdsmdg.aridj.lib.transformations.RotationTransformation
import com.sdsmdg.aridj.lib.transformations.ScaleTransformation
import java.util.*

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

        navDrawer.setTransformation(CombineTransformation(
                Arrays.asList(ScaleTransformation(),
                        RotationTransformation())))

        navDrawer.addMenus(menuIds)
        addDrawer(navDrawer)
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