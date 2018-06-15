package com.sdsmdg.aridj.lib.transformations

import android.view.View

class CombineTransformation(private val transformations: List<Transformation>): Transformation {

    override fun transform(progress: Float, view: View) {
        for (transformation in transformations) {
            transformation.transform(progress, view)
        }
    }

}