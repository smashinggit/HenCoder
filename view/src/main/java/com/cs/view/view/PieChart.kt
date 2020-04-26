package com.cs.view.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.cs.view.util.dp2px

class PieChart : View {
    private val RADIUS = dp2px(150f)
    private val LENGTH = dp2px(10f)
    private val SELECTED = 2


    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bounds = RectF()
    private val angles = arrayOf(60, 100, 120, 80)
    private val colors = arrayOf(
        Color.parseColor("#2979FF"),
        Color.parseColor("#C2185B"),
        Color.parseColor("#009688"),
        Color.parseColor("#FF8F00")
    )

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds.set(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var currentAngle = 0
        for (i in angles.indices) {
            mPaint.color = colors[i]

            canvas.save()
            if (SELECTED == i) {
                canvas.translate(
                    (Math.cos(Math.toRadians((currentAngle + angles[i] / 2).toDouble())) * LENGTH).toFloat(),
                    (Math.sin(Math.toRadians((currentAngle + angles[i] / 2).toDouble())) * LENGTH).toFloat()
                )
            }
            canvas.drawArc(
                bounds, currentAngle.toFloat(),
                angles[i].toFloat(), true, mPaint
            )
            canvas.restore()
            currentAngle += angles[i]
        }
    }
}