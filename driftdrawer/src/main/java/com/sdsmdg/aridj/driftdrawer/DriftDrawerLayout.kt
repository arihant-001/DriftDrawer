package com.sdsmdg.aridj.driftdrawer

import android.content.Context
import android.support.v4.widget.DrawerLayout

class DriftDrawerLayout(context: Context) : DrawerLayout(context) {

    private lateinit var navLayout: DriftNavLayout

    fun setNavLayout(navLayout: DriftNavLayout) {
        this.navLayout = navLayout
    }

    override fun openDrawer(gravity: Int) {
        navLayout.openDrawer()
    }

    override fun closeDrawer(gravity: Int) {
        navLayout.closeDrawer()
    }

    override fun isDrawerVisible(drawerGravity: Int): Boolean {
        return !navLayout.isClosed
    }

    override fun getDrawerLockMode(edgeGravity: Int): Int {
        return LOCK_MODE_UNLOCKED
    }
}