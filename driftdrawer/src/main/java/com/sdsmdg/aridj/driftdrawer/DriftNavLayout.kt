package com.sdsmdg.aridj.driftdrawer

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
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
import android.widget.ScrollView
import com.sdsmdg.aridj.driftdrawer.transformations.Transformation

private const val STATE_SUPER = "super_state"
private const val STATE_IS_CLOSED = "is_opened"
private const val STATE_SELECTED_ITEM = "selected_item"

class DriftNavLayout(ctx: Context) : FrameLayout(ctx),
    DriftDrawer {

    private val dragHelper: ViewDragHelper
    private var transformation: Transformation ?= null
    private lateinit var mainView: View

    private var drawerListener: DrawerLayout.DrawerListener? = null
    var navItemClickListener: (Int, View)->Unit
    private val parentLayout: LinearLayout
    private var menus: ArrayList<LinearLayout>

    var maxHorizontalDrag: Int = 150
    var dragProgress: Float = 0f
    var dragState: Int = ViewDragHelper.STATE_IDLE
    var navColor: Int = Color.TRANSPARENT
    var itemHighlightColor: Int = Color.BLACK
    private var selectedItemPosition: Int = -1
    var isClosed: Boolean = true

    init {
        dragHelper = ViewDragHelper.create(this, 1.0f, ViewDragCallback())
        menus = ArrayList()
        parentLayout = LinearLayout(context)
        navItemClickListener = { _, _ -> }
    }

    fun setMainView(mainView: View) {
        this.mainView = mainView
    }

    fun setDrawerListener(listener: DrawerLayout.DrawerListener) {
        this.drawerListener = listener
    }

    fun setTransformation(transformation: Transformation) {
        this.transformation = transformation
    }

    override fun closeDrawer() {
        if (dragHelper.smoothSlideViewTo(mainView, 0, mainView.top)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun closeDrawer(animated: Boolean) {
        if(animated) {
            closeDrawer()
        } else{
            isClosed = true
            dragProgress = 0f
            requestLayout()
            drawerListener?.onDrawerSlide(parentLayout, 0f)
        }
    }

    override fun openDrawer() {
        if (dragHelper.smoothSlideViewTo(mainView, maxHorizontalDrag, mainView.top)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun openDrawer(animated: Boolean) {
        if(animated) {
            openDrawer()
        } else {
            isClosed = false
            dragProgress = 1f
            requestLayout()
            drawerListener?.onDrawerSlide(parentLayout, maxHorizontalDrag.toFloat())
        }
    }

    override fun isDrawerClosed(): Boolean {
        return isClosed
    }

    override fun getLayout(): DriftNavLayout {
        return this
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setBackgroundColor(navColor)
        if(!isClosed) {
            openDrawer(false)
        }
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
        val scrollView = ScrollView(context)
        val linearLayoutParams = LinearLayout.LayoutParams(maxHorizontalDrag, LinearLayout.LayoutParams.MATCH_PARENT)
        parentLayout.orientation = LinearLayout.VERTICAL
        linearLayoutParams.gravity = Gravity.CENTER_HORIZONTAL
        parentLayout.layoutParams = linearLayoutParams
        parentLayout.gravity = Gravity.CENTER_HORIZONTAL
        for (i in 0 until menuIds.size) {
            val menuLayout = LinearLayout(context)
            val menuParams = LinearLayout.LayoutParams(maxHorizontalDrag, maxHorizontalDrag)
            menuLayout.gravity = Gravity.CENTER
            val imagesView = ImageView(context)
            val params = ViewGroup.LayoutParams((maxHorizontalDrag*0.7f).toInt(), (maxHorizontalDrag*0.7f).toInt())
            imagesView.setImageResource(menuIds[i])
            parentLayout.addView(menuLayout, menuParams)
            menuLayout.addView(imagesView, params)
            menus.add(menuLayout)

            menuLayout.setOnClickListener {
                navItemClickListener.invoke(i, menuLayout)
                selectItem(i)
                closeDrawer()
            }
        }
        addView(scrollView)
        scrollView.addView(parentLayout, linearLayoutParams)

        selectItem(selectedItemPosition)
    }

    private fun selectItem(position: Int) {
        this.selectedItemPosition = position
        clearSelectedItem()
        if (position != -1) {
            menus[position].setBackgroundColor(itemHighlightColor)
        }
    }

    private fun clearSelectedItem() {
        for (menu in menus) {
            menu.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun setSelectedPosition(position: Int) {
        selectItem(position)
    }

    override fun onSaveInstanceState(): Parcelable? {
        super.onSaveInstanceState()
        val savedState = Bundle()
        savedState.putParcelable(STATE_SUPER, super.onSaveInstanceState())
        savedState.putBoolean(STATE_IS_CLOSED, isClosed)
        savedState.putInt(STATE_SELECTED_ITEM, selectedItemPosition)
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val savedState = state as Bundle
        super.onRestoreInstanceState(savedState.getParcelable(STATE_SUPER))
        isClosed = savedState.getBoolean(STATE_IS_CLOSED, true)
        selectedItemPosition = savedState.getInt(STATE_SELECTED_ITEM, -1)

        selectItem(selectedItemPosition)
        if (!isClosed) {
            openDrawer(false)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child == mainView) {
                var rootLeft = 0
                if (!isClosed) {
                    rootLeft = maxHorizontalDrag
                    transformViews()
                }
                child.layout(rootLeft, top, rootLeft + (right - left), bottom)
            } else {
                child.layout(left, top, right, bottom)
            }
        }
    }

    private inner class ViewDragCallback : ViewDragHelper.Callback() {

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

        override fun getViewHorizontalDragRange(child: View): Int {
            return if (child === mainView) maxHorizontalDrag else 0
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return Math.max(0, Math.min(left, maxHorizontalDrag))
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            if (xvel > 0) {
                dragHelper.settleCapturedViewAt(maxHorizontalDrag, mainView.top)
            } else {
                dragHelper.settleCapturedViewAt(0, mainView.top)
            }
            invalidate()
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            dragProgress = left.toFloat() / maxHorizontalDrag
            drawerListener?.onDrawerSlide(parentLayout, dragProgress)
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
                    drawerListener?.onDrawerClosed(parentLayout)
                } else {
                    drawerListener?.onDrawerOpened(parentLayout)
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
