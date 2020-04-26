package com.cs.view.view.drag

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import androidx.customview.widget.ViewDragHelper
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import androidx.annotation.RequiresApi


class DragHelperGridView : ViewGroup {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val COLUMNS = 2
    private val ROWS = 3

    val viewConfiguration = ViewConfiguration.get(context)

    val viewDragHelper = ViewDragHelper.create(this, DragCallBack())


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(specWidth, specHeight)

        val childWidth = specWidth / COLUMNS
        val childHeight = specHeight / ROWS

        measureChildren(
            MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY)
            , MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        var childTop = 0
        val childWidth = measuredWidth / COLUMNS
        val childHeight = measuredHeight / ROWS

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            if (i % COLUMNS == 0) {
                child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)

            } else {
                childLeft += childWidth //向左移动一个子view的宽度
                child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)

                childLeft = 0            //重新开始一行，左边重置为0
                childTop += childHeight  //向下移动一个子view的高度
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return viewDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        viewDragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        if (viewDragHelper.continueSettling(true)) {
            postInvalidateOnAnimation()
        }
    }


    inner class DragCallBack : ViewDragHelper.Callback() {
        var capturedLeft = 0
        var capturedTop = 0

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)

            when (state) {
                ViewDragHelper.STATE_IDLE -> {
                    val capturedView = viewDragHelper.capturedView
                    capturedView?.let {
                        it.elevation = it.elevation - 1
                    }
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            super.onViewCaptured(capturedChild, activePointerId)
            capturedChild.elevation = capturedChild.elevation + 1
            capturedLeft = capturedChild.left
            capturedTop = capturedChild.top
        }


        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            viewDragHelper.settleCapturedViewAt(capturedLeft, capturedTop)
            postInvalidateOnAnimation()
        }
    }


}