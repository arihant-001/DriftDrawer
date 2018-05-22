package com.sdsmdg.aridj.lib

import android.content.Context
import android.graphics.Color
import android.widget.FrameLayout

class PopOutNavLayout(ctx: Context): FrameLayout(ctx) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        setBackgroundColor(Color.DKGRAY)
    }

}
