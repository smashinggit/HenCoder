package com.cs.view.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.util.forEach
import com.cs.view.util.dp2px

/**
 *  多点触控
 *  多个手指互不干扰，各干各的
 */
class MultiTouchView3 : View {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private var paths = SparseArray<Path>()

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = dp2px(4f)
        mPaint.color = Color.RED
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeJoin = Paint.Join.ROUND
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paths.forEach { _, value ->
            canvas.drawPath(value, mPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_POINTER_DOWN -> {
                val index = event.actionIndex
                val pointerId = event.getPointerId(index)
                val path = Path()
                path.moveTo(event.getX(index), event.getY(index))

                paths.append(pointerId, path)
            }

            MotionEvent.ACTION_MOVE -> {
                for (i in 0 until event.pointerCount) {
                    val pointerId = event.getPointerId(i)
                    val path = paths[pointerId]
                    path.lineTo(event.getX(i), event.getY(i))
                }
                invalidate()
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_POINTER_UP -> {
                val pointerId = event.getPointerId(event.actionIndex)
                paths.remove(pointerId)
                invalidate()
            }
        }
        return true
    }
}