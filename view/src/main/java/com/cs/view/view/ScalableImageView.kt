package com.cs.view.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.annotation.RequiresApi
import androidx.core.view.GestureDetectorCompat
import com.cs.view.R
import com.cs.view.util.dp2px

class ScalableImageView : View {


    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val WIDTH = dp2px(300f)
    private val OVER_SCALE = 1.5f

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBitmap = getBitmap(WIDTH.toInt())

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f

    //放大缩小
    private var smallScale = 0f
    private var bigScale = 0f
    private var isBig = false

    //动画
    private var currentScale = 0f //当前的缩放值
    private var scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 1f)

    //手势
    private val mDetectorListener = GestureDetectorListener()
    private val mFlingRunnable = FlingRunnable()
    private val mDetector = GestureDetectorCompat(context, mDetectorListener)

    //手势缩放
    private val mScaleListener = ScaleGestureDetectorListener()
    private val mScaleGestureDetector = ScaleGestureDetector(context, mScaleListener)

    //惯性滑动
    private val scroller = OverScroller(context)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (width - mBitmap.width) / 2f
        originalOffsetY = (height - mBitmap.height) / 2f

        if (mBitmap.width / mBitmap.height.toFloat() > width - height.toFloat()) {
            smallScale = width / mBitmap.width.toFloat()
            bigScale = height / mBitmap.height.toFloat() * OVER_SCALE
        } else {
            smallScale = height / mBitmap.height.toFloat()
            bigScale = width / mBitmap.width.toFloat() * OVER_SCALE
        }

        currentScale = smallScale
        scaleAnimator.setFloatValues(smallScale, bigScale)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        canvas.scale(currentScale, currentScale, (width / 2).toFloat(), (height / 2).toFloat())
        canvas.drawBitmap(mBitmap, originalOffsetX, originalOffsetY, mPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var result = mScaleGestureDetector.onTouchEvent(event)
        if (!mScaleGestureDetector.isInProgress) {
            result = mDetector.onTouchEvent(event)
        }
        return result
    }


    //动画
    fun setCurrentScale(currentScale: Float) {
        this.currentScale = currentScale
        invalidate()
    }

    fun getCurrentScale(): Float {
        return currentScale
    }

    inner class GestureDetectorListener : GestureDetector.SimpleOnGestureListener() {
        //双击
        override fun onDoubleTap(e: MotionEvent): Boolean {
            isBig = !isBig
            if (scaleAnimator.isRunning) return false
            if (isBig) {
                offsetX = (e.x - width / 2f) - (e.x - width / 2f) * bigScale / smallScale
                offsetY = (e.y - height / 2f) - (e.y - height / 2f) * bigScale / smallScale
                fixOffset()
                scaleAnimator.start()  //放大动画
            } else {
                scaleAnimator.reverse()  //缩小动画
            }
            return false
        }

        override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
            return false
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            return false
        }

        override fun onShowPress(e: MotionEvent?) {
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return false
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        //惯性滑动
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (isBig) {
                scroller.fling(
                    offsetX.toInt(),
                    offsetY.toInt(),
                    velocityX.toInt(),
                    velocityY.toInt(),
                    (-(mBitmap.width * bigScale - width) / 2).toInt(),
                    ((mBitmap.width * bigScale - width) / 2).toInt(),
                    (-(mBitmap.height * bigScale - height) / 2).toInt(),
                    ((mBitmap.height * bigScale - height) / 2).toInt()
                )

                postOnAnimation(mFlingRunnable)
            }
            return false
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (isBig) {
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffset()
                invalidate()
            }
            return false
        }

        override fun onLongPress(e: MotionEvent?) {
        }
    }

    inner class FlingRunnable : Runnable {
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun run() {
            if (scroller.computeScrollOffset()) { //动画是否还在进行中
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                postOnAnimation(mFlingRunnable)
            }
        }
    }

    inner class ScaleGestureDetectorListener : ScaleGestureDetector.OnScaleGestureListener {
        private var initialScale = 0f
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            initialScale = currentScale
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            currentScale = initialScale * detector.scaleFactor
            if (currentScale > bigScale) {
                currentScale = bigScale
            }
            if (currentScale < smallScale) {
                currentScale = smallScale
            }
            invalidate()
            return false
        }
    }

    //修正边界
    fun fixOffset() {
        if (offsetX > (mBitmap.width * bigScale - width) / 2) {
            offsetX = (mBitmap.width * bigScale - width) / 2
        }
        if (offsetX < -(mBitmap.width * bigScale - width) / 2) {
            offsetX = -(mBitmap.width * bigScale - width) / 2
        }
        if (offsetY > (mBitmap.height * bigScale - height) / 2) {
            offsetY = (mBitmap.height * bigScale - height) / 2
        }
        if (offsetY < -(mBitmap.height * bigScale - height) / 2) {
            offsetY = -(mBitmap.height * bigScale - height) / 2
        }
    }


    private fun getBitmap(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avata, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.pic, options)
    }


}