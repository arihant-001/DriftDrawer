package com.sdsmdg.aridj.driftdrawer

interface DriftDrawer {

    fun isDrawerClosed(): Boolean

    fun closeDrawer()

    fun closeDrawer(animated: Boolean)

    fun openDrawer()

    fun openDrawer(animated: Boolean)

    fun getLayout(): DriftNavLayout

    fun setSelectedPosition(position: Int)
}