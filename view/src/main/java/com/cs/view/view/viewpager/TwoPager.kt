package com.cs.view.view.viewpager

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.annotation.RequiresApi
import com.cs.view.util.log

/**
 * 只有两个子 view 的 ViewPager
 *
 */
class TwoPager : ViewGroup {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)


    var downX = 0f
    var downY = 0f
    var downScrollX = 0
    var isScrolling = false

    var viewConfiguration = ViewConfiguration.get(context)
    val overScroller = OverScroller(context)
    val velocityTracker = VelocityTracker.obtain()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        val top = 0
        var right = width
        val bottom = height

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.layout(left, top, right, bottom)

            left += width
            right += width
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(ev)

        var result = false
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                isScrolling = false
                downX = ev.x
                downY = ev.y
                downScrollX = scrollX
            }

            MotionEvent.ACTION_MOVE -> {
                val dx = ev.x - downX

                if (!isScrolling) {
                    //左右滑动距离超过阈值，则拦截事件
                    if (Math.abs(dx) > viewConfiguration.scaledPagingTouchSlop) {
                        log("onInterceptTouchEvent 拦截事件")
                        parent.requestDisallowInterceptTouchEvent(true)
                        isScrolling = true
                        result = true
                    }
                }
            }
        }
        return result
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onTouchEvent(ev: MotionEvent): Boolean {

        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(ev)

        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                isScrolling = false
                downX = ev.x
                downY = ev.y
                downScrollX = scrollX
            }

            MotionEvent.ACTION_MOVE -> {
                // (ev.x - downX) 当手指按下并移动时，此值代表滑动的距离
                // 当手指向左滑动时，此指为负值
                // 此需求是手指向左，view也向左
                // scrollTo 方法中 参数值为正值，代表view向左滑动，
                // 所以 dx = -(ev.x - downX)
                var dx = -(ev.x - downX) + downScrollX

                if (dx < 0) {
                    dx = 0f
                } else if (dx > width) {
                    dx = width.toFloat()
                }
                // 向右滑动，dx大于0
                scrollTo(dx.toInt(), 0)
            }

            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(
                    1000,
                    viewConfiguration.scaledMaximumFlingVelocity.toFloat()
                )

                val vx = velocityTracker.xVelocity

                val targetPage = if (Math.abs(vx) < viewConfiguration.scaledMinimumFlingVelocity) {
                    if (scrollX < width / 2) 0 else 1
                } else {
                    if (vx < 0) 1 else 0
                }

                val scrollDistance = if (targetPage == 0) -scrollX else width - scrollX
                overScroller.startScroll(scrollX, 0, scrollDistance, 0)
                postInvalidateOnAnimation()
            }
        }

        return true
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun computeScroll() {
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.currX, overScroller.currY)
            postInvalidateOnAnimation()
        }
    }

}