package com.sdsmdg.aridj.driftdrawer.transformations

import android.view.View

class ScaleTransformation: Transformation {

    override fun transform(progress: Float, view: View) {
        val scale = 1/8f + progress * (1f - 1/8f)
        view.scaleX = scale
        view.scaleY = scale

//        val scale = 1f + progress * (8f - 1f)
//        view.layoutParams.height = (scale*view.layoutParams.height).toInt()
//        view.layoutParams.width = (scale*view.layoutParams.width).toInt()
//        view.invalidate()
    }

}