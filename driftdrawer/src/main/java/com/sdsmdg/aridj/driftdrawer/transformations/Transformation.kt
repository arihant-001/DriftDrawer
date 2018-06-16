package com.sdsmdg.aridj.driftdrawer.transformations

import android.view.View

interface Transformation {

    fun transform(progress: Float, view: View)

}