package com.sdsmdg.aridj.lib

interface PopOutDrawer {

    var isClosed: Boolean

    fun closeDrawer()

    fun closeDrawer(animated: Boolean)

    fun openDrawer()

    fun openDrawer(animated: Boolean)

    fun getLayout(): PopOutNavLayout

    fun setSelectedPosition(position: Int)
}