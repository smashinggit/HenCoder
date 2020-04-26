package com.cs.view.view.drag

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.customview.widget.ViewDragHelper


class DrawerLayout : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val MIN_DRAWER_MARGIN = 64 // dp


    private val mViewDragHelper = ViewDragHelper.create(this, ViewDragCallBack())

    private var mMinDrawerMargin = 0  //drawer离父容器右边的最小外边距
    private lateinit var mLeftMenuView: View
    private lateinit var mContentView: View

    init {

        val density = resources.displayMetrics.density
        mMinDrawerMargin = (MIN_DRAWER_MARGIN * density + 0.5f).toInt()

        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mContentView = getChildAt(0)
        mLeftMenuView = getChildAt(1)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(widthSize, heightSize)

        val contentLayoutParams = mContentView.layoutParams as MarginLayoutParams
        val contentWidthSpec = ViewGroup.getChildMeasureSpec(
            widthMeasureSpec,
            contentLayoutParams.leftMargin + contentLayoutParams.rightMargin,
            contentLayoutParams.width
        )
        val contentHeightSpec = ViewGroup.getChildMeasureSpec(
            heightMeasureSpec,
            contentLayoutParams.topMargin + contentLayoutParams.bottomMargin,
            contentLayoutParams.height
        )
        mContentView.measure(contentWidthSpec, contentHeightSpec)


        val menuLayoutParams = mLeftMenuView.layoutParams as MarginLayoutParams
        val drawerWidthSpec = ViewGroup.getChildMeasureSpec(
            widthMeasureSpec,
            mMinDrawerMargin + menuLayoutParams.leftMargin + menuLayoutParams.rightMargin,
            menuLayoutParams.width
        )
        val drawerHeightSpec = ViewGroup.getChildMeasureSpec(
            heightMeasureSpec,
            menuLayoutParams.topMargin + menuLayoutParams.bottomMargin,
            menuLayoutParams.height
        )
        mLeftMenuView.measure(drawerWidthSpec, drawerHeightSpec)

        //因为xml布局中MenuLayout的宽度是Math_Parent,
        //所以如果调用measureChildren 方法的话，
        // 得到的 MenuLayout 宽度就占满了屏幕，所以需要手动测量
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        measureChildren(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val contentLayoutParams = mContentView.layoutParams as MarginLayoutParams
        val menuLayoutParams = mLeftMenuView.layoutParams as MarginLayoutParams

        mContentView.layout(
            contentLayoutParams.leftMargin,
            contentLayoutParams.topMargin,
            contentLayoutParams.leftMargin + mContentView.measuredWidth,
            contentLayoutParams.topMargin + mContentView.measuredHeight
        )

        val left = -mLeftMenuView.measuredWidth
        mLeftMenuView.layout(
            left,
            menuLayoutParams.topMargin,
            left + mLeftMenuView.measuredWidth,
            menuLayoutParams.topMargin + mLeftMenuView.measuredHeight
        )
    }


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mViewDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mViewDragHelper.processTouchEvent(event)
        return true
    }

    inner class ViewDragCallBack : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child == mLeftMenuView
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            //手指释放时,隐藏或者显示

            //计算当前移动的百分比  完全隐藏时是0，完全显示时是1
            val fraction = (releasedChild.width + releasedChild.left) * 1.0f / releasedChild.width
            //xvel==0时，代表手指一直按着，xvel有值时，代表手指离开时有惯性滑动
            mViewDragHelper.settleCapturedViewAt(
                if (xvel > 0 || xvel == 0f && fraction > 0.5f) 0 else -releasedChild.width,
                releasedChild.top
            )
            invalidate()
        }

        //在边界拖动时回调
        override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
            mViewDragHelper.captureChildView(mLeftMenuView, pointerId)
        }

        //水平移动的范围
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            //比如横向的情况下，我希望只在ViewGroup的内部移动，
            // 即：最小>=paddingleft，最大<=ViewGroup.getWidth()-paddingright-child.getWidth
            val leftBound = -child.width
            val rightBound = 0

            return when {
                left > rightBound -> 0
                left < leftBound -> leftBound
                else -> left
            }
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return measuredWidth - child.measuredWidth
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return measuredHeight - child.measuredHeight
        }

    }

    override fun computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate()
        }
    }


    fun openDrawer() {
        mViewDragHelper.smoothSlideViewTo(mLeftMenuView, 0, mLeftMenuView.top)
        invalidate()
    }

    fun closeDrawer() {
        mViewDragHelper.smoothSlideViewTo(mLeftMenuView, -mLeftMenuView.width, mLeftMenuView.top)
        invalidate()
    }

}