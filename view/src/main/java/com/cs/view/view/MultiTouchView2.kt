package com.cs.view.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import com.cs.view.R
import com.cs.view.util.dp2px

/**
 *  多点触控
 *  多个手指协作
 */
class MultiTouchView2 : View {

    private val WIDTH = dp2px(300f)

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mAvatar = getAvatar(WIDTH.toInt())


    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    var downX = 0f
    var downY = 0f
    var offsetX = 0f
    var offsetY = 0f
    var lastOffsetX = 0f
    var lastOffsetY = 0f


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(mAvatar, offsetX, offsetY, mPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var sumX = 0f
        var sumY = 0f
        var pointerCount = event.pointerCount

        val isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP
        for (i in 0 until pointerCount) {
            if (!(isPointerUp && event.actionIndex == i)) {
                sumX += event.getX(i)
                sumY += event.getY(i)
            }
        }
        if (isPointerUp) {
            pointerCount -= 1
        }
        val focusX = sumX / pointerCount
        val focusY = sumY / pointerCount

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_POINTER_DOWN,
            MotionEvent.ACTION_POINTER_UP
            -> {
                downX = focusX
                downY = focusY
                lastOffsetX = offsetX
                lastOffsetY = offsetY
            }

            MotionEvent.ACTION_MOVE -> {
                // focusX - downX 是由于手指移动导致的偏移
                // lastOffsetX 是上次移动的偏移量
                offsetX = focusX - downX + lastOffsetX
                offsetY = focusY - downY + lastOffsetY
                invalidate()
            }

        }
        return true
    }

    private fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avata, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avata, options)
    }
}