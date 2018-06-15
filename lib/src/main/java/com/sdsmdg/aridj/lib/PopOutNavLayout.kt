package com.sdsmdg.aridj.lib

import android.content.Context
import android.graphics.Color
import android.support.v4.view.ViewCompat
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.ViewDragHelper
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.sdsmdg.aridj.lib.transformations.Transformation

class PopOutNavLayout(ctx: Context, private val mainView: View) : FrameLayout(ctx) {

    private val dragHelper: ViewDragHelper
    private var transformation: Transformation ?= null

    private var drawerListener: DrawerLayout.DrawerListener? = null
    private lateinit var itemClickListener: (Int, View)->Unit
    private var linearLayout: LinearLayout? = null
    private var menus: ArrayList<LinearLayout>

    val maxHorizontalDrag: Int = 150
    var isClosed: Boolean = true
    var dragProgress: Float = 0f
    var dragState: Int = ViewDragHelper.STATE_IDLE

    init {
        dragHelper = ViewDragHelper.create(this, 1.0f, ViewDragCallback())
        menus = ArrayList()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setBackgroundColor(Color.parseColor("#163028"))
    }

    fun open() {
        if (dragHelper.smoothSlideViewTo(mainView, maxHorizontalDrag, mainView.top)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    fun close() {
        if (dragHelper.smoothSlideViewTo(mainView, 0, mainView.top)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    fun setDrawerListener(listener: DrawerLayout.DrawerListener) {
        this.drawerListener = listener
    }

    fun setTransformation(transformation: Transformation) {
        this.transformation = transformation
    }

    fun setItemClickListener(itemClickListener: (Int, View) -> Unit) {
        this.itemClickListener = itemClickListener
    }

    override fun computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (dragHelper.shouldInterceptTouchEvent(event)) {
            true
        } else super.onInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    fun addMenus(menuIds: ArrayList<Int>) {
        val linearLayout = LinearLayout(context)
        val linearLayoutParams = LinearLayout.LayoutParams(maxHorizontalDrag, LinearLayout.LayoutParams.MATCH_PARENT)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayoutParams.gravity = Gravity.CENTER_HORIZONTAL
        linearLayout.layoutParams = linearLayoutParams
        linearLayout.gravity = Gravity.CENTER_HORIZONTAL
        for (i in 0 until menuIds.size) {
            val menuLayout = LinearLayout(context)
            val menuParams = LinearLayout.LayoutParams(80, 80)
            menuLayout.gravity = Gravity.CENTER
            menuParams.bottomMargin = 15
            menuParams.topMargin = 15
            val imagesView = ImageView(context)
            val params = ViewGroup.LayoutParams(80, 80)
            imagesView.setImageResource(menuIds[i])
            linearLayout.addView(menuLayout, menuParams)
            menuLayout.addView(imagesView, params)
            menus.add(menuLayout)

            menuLayout.setOnClickListener {
                itemClickListener?.invoke(i, menuLayout)
            }
        }
        this.linearLayout = linearLayout
        addView(linearLayout, linearLayoutParams)
    }

    private inner class ViewDragCallback : ViewDragHelper.Callback() {

        var edgeTouched: Boolean = true

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            if (isClosed) {
                return child === mainView
            } else {
                if (child != mainView) {
                    dragHelper.captureChildView(mainView, pointerId)
                    return false
                }
                return true
            }
        }

        override fun onEdgeTouched(edgeFlags: Int, pointerId: Int) {
            edgeTouched = true
        }

        override fun getViewHorizontalDragRange(child: View?): Int {
            return if (child === mainView) maxHorizontalDrag else 0
        }

        override fun clampViewPositionHorizontal(child: View?, left: Int, dx: Int): Int {
            return Math.max(0, Math.min(left, maxHorizontalDrag))
        }

        override fun onViewReleased(releasedChild: View?, xvel: Float, yvel: Float) {
            if (xvel > 0) {
                dragHelper.settleCapturedViewAt(maxHorizontalDrag, mainView.top)
            } else {
                dragHelper.settleCapturedViewAt(0, mainView.top)
            }
            invalidate()
        }

        override fun onViewPositionChanged(changedView: View?, left: Int, top: Int, dx: Int, dy: Int) {
            dragProgress = left.toFloat() / maxHorizontalDrag
            drawerListener?.onDrawerSlide(linearLayout, dragProgress)
            transformViews()
            invalidate()
        }

        override fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            isClosed = dragProgress == 0f
            if (state != ViewDragHelper.STATE_IDLE && dragState == ViewDragHelper.STATE_IDLE) {
                // starts
                drawerListener?.onDrawerStateChanged(DrawerLayout.STATE_DRAGGING)
            } else if (state == ViewDragHelper.STATE_IDLE && dragState != ViewDragHelper.STATE_IDLE) {
                // ends
                if (isClosed) {
                    drawerListener?.onDrawerClosed(linearLayout)
                } else {
                    drawerListener?.onDrawerOpened(linearLayout)
                }

                drawerListener?.onDrawerStateChanged(DrawerLayout.STATE_IDLE)
            }
            dragState = state
        }
    }

    private fun transformViews() {
        for (i in 0 until menus.size) {
            var progress = (dragProgress - i.toFloat()/menus.size)/(1f - i.toFloat()/menus.size)
            progress = Math.max(0f, progress)
            transformation?.transform(progress, menus[i].getChildAt(0))
        }
    }
}
