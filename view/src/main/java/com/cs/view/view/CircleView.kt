package com.cs.view.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.cs.view.util.dp2px

class CircleView : ImageView {

    private val RADIUS = dp2px(100f)
    private val PADDING = dp2px(20f)
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)


    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        mPaint.color = Color.RED
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension((RADIUS * 2 + PADDING * 2).toInt(), (RADIUS * 2 + PADDING * 2).toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.GRAY)
        canvas.drawCircle(RADIUS + PADDING, RADIUS + PADDING, RADIUS, mPaint)
    }
}