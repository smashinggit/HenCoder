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
 *  多个手指接力
 */
class MultiTouchView : View {

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
    var trackingPointerId = 0


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(mAvatar, offsetX, offsetY, mPaint)
        Log.e("tag", "offsetX $offsetX")
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                trackingPointerId = event.getPointerId(0)
                downX = event.getX(0)
                downY = event.getY(0)
                lastOffsetX = offsetX
                lastOffsetY = offsetY
            }

            MotionEvent.ACTION_MOVE -> {
                // event.x - downX 是由于手指移动导致的偏移
                // lastOffsetX 是上次移动的偏移量
                val index = event.findPointerIndex(trackingPointerId)
                offsetX = event.getX(index) - downX + lastOffsetX
                offsetY = event.getY(index) - downY + lastOffsetY
                invalidate()
            }

            MotionEvent.ACTION_POINTER_DOWN -> { //多指按下
                val actionIndex = event.actionIndex  //获取到新按下手指的index
                trackingPointerId = event.getPointerId(actionIndex)
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
                lastOffsetX = offsetX
                lastOffsetY = offsetY
            }

            MotionEvent.ACTION_POINTER_UP -> { // 多指状态下，有手指抬起
                val actionIndex = event.actionIndex   //获取抬起手指的index
                val pointerId = event.getPointerId(actionIndex)

                if (pointerId == trackingPointerId) { //如果抬起的手指是正在追踪的手指

                    //后续需要追踪的index
                    val newIndex =
                        if (actionIndex == event.pointerCount - 1) { //如果抬起的手指是最后那个
                            event.pointerCount - 2      //赋值给倒数第二按下的手指
                        } else {
                            event.pointerCount - 1
                        }

                    trackingPointerId = event.getPointerId(newIndex)
                    downX = event.getX(newIndex)
                    downY = event.getY(newIndex)
                    lastOffsetX = offsetX
                    lastOffsetY = offsetY
                }
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