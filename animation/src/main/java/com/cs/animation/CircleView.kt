package com.cs.animation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.cs.animation.util.dp2px

class CircleView : View {
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var radius = dp2px(50f)
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        mPaint.color = Color.RED
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(width / 2f, height / 2f, radius, mPaint)
    }
}