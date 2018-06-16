package com.sdsmdg.aridj.driftdrawer.transformations

import android.view.View

class RotationTransformation: Transformation {

    override fun transform(progress: Float, view: View) {
        val rotation = 0 + progress * (360 - 0)
        view.rotation = rotation
    }

}