package com.cs.view.view.drag

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.customview.widget.ViewDragHelper
import com.cs.view.R

class DragUpDownLayout : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val viewConfiguration = ViewConfiguration.get(context)
    private val viewDragHelper = ViewDragHelper.create(this, DragHelper())
    private lateinit var dragView: View

    override fun onFinishInflate() {
        super.onFinishInflate()
        dragView = findViewById<View>(R.id.dragView)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return viewDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        viewDragHelper.processTouchEvent(event)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun computeScroll() {
        super.computeScroll()
        if (viewDragHelper.continueSettling(true)) {
            postInvalidateOnAnimation()
        }
    }

    inner class DragHelper : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child == dragView
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return when {
                top <= 0 -> 0
                top >= height - dragView.height -> height - dragView.height
                else -> top
            }
        }

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)

            if (Math.abs(yvel) > viewConfiguration.scaledMinimumFlingVelocity) {

                if (yvel > 0) {  //向下
                    viewDragHelper.settleCapturedViewAt(0, height - dragView.height)

                } else {
                    viewDragHelper.settleCapturedViewAt(0, 0)
                }

            } else {
                if (releasedChild.top > releasedChild.height) {
                    viewDragHelper.settleCapturedViewAt(0, height - dragView.height)
                } else {
                    viewDragHelper.settleCapturedViewAt(0, 0)
                }
            }
            postInvalidateOnAnimation()
        }

    }

}