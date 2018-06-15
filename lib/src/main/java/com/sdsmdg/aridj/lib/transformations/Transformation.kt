package com.sdsmdg.aridj.lib.transformations

import android.view.View

interface Transformation {

    fun transform(progress: Float, view: View)

}