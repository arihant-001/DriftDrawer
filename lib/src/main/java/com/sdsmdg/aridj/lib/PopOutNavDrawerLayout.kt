package com.sdsmdg.aridj.lib

import android.content.Context
import android.support.v4.widget.DrawerLayout

class PopOutNavDrawerLayout(context: Context?) : DrawerLayout(context) {

    var navDrawerLayout: PopOutNavLayout ?= null

    fun setDrawer(navDrawer: PopOutNavLayout) {
        this.navDrawerLayout = navDrawer
    }

    override fun openDrawer(gravity: Int) {
        navDrawerLayout?.open()
    }

    override fun closeDrawer(gravity: Int) {
        navDrawerLayout?.close()
    }

    override fun isDrawerVisible(drawerGravity: Int): Boolean {
        return false
    }

    override fun getDrawerLockMode(edgeGravity: Int): Int {
        return DrawerLayout.LOCK_MODE_LOCKED_OPEN
    }
}