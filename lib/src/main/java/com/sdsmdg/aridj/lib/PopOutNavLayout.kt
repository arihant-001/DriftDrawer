package com.sdsmdg.aridj.lib

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.ViewDragHelper
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

class PopOutNavLayout(ctx: Context, private val mainView: View) : FrameLayout(ctx) {

    private val dragHelper: ViewDragHelper
    private var drawerListener: DrawerLayout.DrawerListener? = null
    private var menu: View? = null

    var isClosed: Boolean = true
    var dragProgress: Float = 0f
    var dragState: Int = ViewDragHelper.STATE_IDLE

    init {
        dragHelper = ViewDragHelper.create(this, 1.0f, ViewDragCallback())
    }

    fun open() {
        if (dragHelper.smoothSlideViewTo(mainView, 100, mainView.top)) {
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

    fun setMenu(menu: View) {
        this.menu = menu
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
            return if (child === mainView) 100 else 0
        }

        override fun clampViewPositionHorizontal(child: View?, left: Int, dx: Int): Int {
            return Math.max(0, Math.min(left, 100))
        }

        override fun onViewReleased(releasedChild: View?, xvel: Float, yvel: Float) {
            if (xvel > 0) {
                dragHelper.settleCapturedViewAt(100, mainView.top)
            } else {
                dragHelper.settleCapturedViewAt(0, mainView.top)
            }
            invalidate()
        }

        override fun onViewPositionChanged(changedView: View?, left: Int, top: Int, dx: Int, dy: Int) {
            dragProgress = left / 100f
            drawerListener?.onDrawerSlide(menu, dragProgress)
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
                    drawerListener?.onDrawerClosed(menu)
                } else {
                    drawerListener?.onDrawerOpened(menu)
                }

                drawerListener?.onDrawerStateChanged(DrawerLayout.STATE_IDLE)
            }
            dragState = state
        }
    }
}
