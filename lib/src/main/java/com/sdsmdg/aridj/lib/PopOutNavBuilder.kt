package com.sdsmdg.aridj.lib

import android.app.Activity
import android.graphics.Color
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import com.sdsmdg.aridj.lib.transformations.CombineTransformation
import com.sdsmdg.aridj.lib.transformations.RotationTransformation
import com.sdsmdg.aridj.lib.transformations.ScaleTransformation
import java.util.*

class PopOutNavBuilder(private val activity: Activity, private val toolbar: Toolbar) {

    private var backgroundColor: Int = Color.TRANSPARENT
    private var itemHighlightColor: Int = Color.BLACK
    private lateinit var itemClickListener: (Int, View)->Unit
    private var menuIds: ArrayList<Int>

    init {
        menuIds = ArrayList()
    }

    fun withMenus(menus: ArrayList<Int>): PopOutNavBuilder {
        this.menuIds = menus

        return this
    }

    fun withColors(backgroundColor: Int , itemHighlightColor: Int): PopOutNavBuilder {
        this.backgroundColor = backgroundColor
        this.itemHighlightColor = itemHighlightColor

        return this
    }

    fun withItemClickListener(itemClickedListener: (Int, View) -> Unit): PopOutNavBuilder {
        this.itemClickListener = itemClickedListener

        return this
    }

    fun build() {
        val contentView = activity.findViewById(android.R.id.content) as ViewGroup
        val rootView = contentView.getChildAt(0)
        contentView.removeAllViews()
        val navDrawer = createNavigationDrawer(rootView)
        contentView.addView(navDrawer)
    }

    private fun createNavigationDrawer(rootView: View): PopOutNavLayout {
        val navDrawer = PopOutNavLayout(activity, rootView)

        navDrawer.navColor = backgroundColor
        navDrawer.itemHighlightColor = itemHighlightColor
        navDrawer.setTransformation(CombineTransformation(
                Arrays.asList(ScaleTransformation(),
                        RotationTransformation())))
        navDrawer.setItemClickListener(itemClickListener)

        navDrawer.addMenus(menuIds)
        addDrawer(navDrawer)
        navDrawer.addView(rootView)

        return navDrawer
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