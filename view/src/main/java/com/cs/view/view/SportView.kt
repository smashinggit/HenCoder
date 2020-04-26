package com.cs.view.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.cs.view.util.dp2px

class SportView : View {

    private val RING_WIDTH = dp2px(20f)
    private val RADIUS = dp2px(150f)
    private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
    private val HIGHLIGHT_COLOR = Color.parseColor("#FF4081")

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)


    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val fontMetrics = Paint.FontMetrics()

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = RING_WIDTH
        mPaint.textSize = dp2px(80f)
        mPaint.textAlign = Paint.Align.CENTER
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //绘制圆环
        mPaint.color = CIRCLE_COLOR
        canvas.drawOval(
            width / 2 - RADIUS,
            height / 2 - RADIUS,
            width / 2 + RADIUS,
            height / 2 + RADIUS,
            mPaint
        )

        //绘制进度条
        mPaint.color = HIGHLIGHT_COLOR
        mPaint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            width / 2 - RADIUS,
            height / 2 - RADIUS,
            width / 2 + RADIUS,
            height / 2 + RADIUS,
            -90f,
            220f,
            false,
            mPaint
        )

        //绘制文字
        mPaint.style = Paint.Style.FILL
        mPaint.getFontMetrics(fontMetrics)
        val offset = (fontMetrics.ascent + fontMetrics.descent) / 2
        canvas.drawText("Sport", (width / 2).toFloat(), (height / 2).toFloat() - offset, mPaint)

    }
}