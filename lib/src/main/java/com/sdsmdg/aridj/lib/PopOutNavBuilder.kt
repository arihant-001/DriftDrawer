package com.sdsmdg.aridj.lib

import android.app.Activity
import android.view.ViewGroup

class PopOutNavBuilder(private val activity: Activity) {

    fun build() {
        val contentView = activity.findViewById(android.R.id.content) as ViewGroup
        val rootView = contentView.getChildAt(0)
        contentView.removeAllViews()
        val navDrawer = PopOutNavLayout(activity)
        navDrawer.addView(rootView)
        contentView.addView(navDrawer)
    }
}