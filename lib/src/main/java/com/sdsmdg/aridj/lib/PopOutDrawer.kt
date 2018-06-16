package com.sdsmdg.aridj.lib

interface PopOutDrawer {

    fun isDrawerClosed(): Boolean

    fun closeDrawer()

    fun closeDrawer(animated: Boolean)

    fun openDrawer()

    fun openDrawer(animated: Boolean)

    fun getLayout(): PopOutNavLayout

    fun setSelectedPosition(position: Int)
}