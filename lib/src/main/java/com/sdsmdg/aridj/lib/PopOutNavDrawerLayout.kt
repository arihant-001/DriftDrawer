package com.sdsmdg.aridj.lib

import android.content.Context
import android.support.v4.widget.DrawerLayout

class PopOutNavDrawerLayout(context: Context, private val navLayout: PopOutNavLayout) : DrawerLayout(context) {

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