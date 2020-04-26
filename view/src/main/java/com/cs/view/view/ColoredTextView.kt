package com.cs.view.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.cs.view.util.dp2px
import kotlin.random.Random

class ColoredTextView : TextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    private val COLORS = arrayOf(
        "#E91E63",
        "#673AB7",
        "#3F51B5",
        "#2196F3",
        "#009688",
        "#FF9800",
        "#FF5722",
        "#795548"
    )

    private val TEXT_SIZE = dp2px(12f)
    private val CONNER_RADIUS = dp2px(6f)
    private val X_PADDING = dp2px(16f)
    private val Y_PADDING = dp2px(8f)
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)


    private fun init() {
        textSize = TEXT_SIZE
        setTextColor(Color.WHITE)
        mPaint.color = Color.parseColor(COLORS[Random.nextInt(COLORS.size)])
        setPadding(X_PADDING.toInt(), Y_PADDING.toInt(), X_PADDING.toInt(), Y_PADDING.toInt())
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            CONNER_RADIUS,
            CONNER_RADIUS,
            mPaint
        )
        Log.e("tag", "mPaint ${mPaint.color}")
        super.onDraw(canvas)
    }


}