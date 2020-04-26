package com.cs.animation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.cs.animation.util.dp2px

class ProvinceView : View {
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val fontMetrics = Paint.FontMetrics()
    private var offset = 0f
    var province = "北京"
        set(value) {
            field = value
            invalidate()
        }

    companion object {
        val PROVINCES = arrayOf(
            "北京",
            "天津",
            "上海",
            "重庆",
            "河北",
            "河南",
            "云南",
            "辽宁",
            "黑龙江",
            "湖南",
            "安徽",
            "山东",
            "新疆",
            "江苏",
            "浙江",
            "江西",
            "湖北",
            "广西",
            "甘肃",
            "山西",
            "内蒙古",
            "陕西",
            "吉林",
            "福建",
            "贵州",
            "广东",
            "青海",
            "西藏",
            "四川",
            "宁夏"
        )
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        mPaint.color = Color.RED
        mPaint.textSize = dp2px(100f)
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.getFontMetrics(fontMetrics)
        offset = (fontMetrics.ascent + fontMetrics.descent) / 2
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(province, (width / 2).toFloat(), (height / 2).toFloat() - offset, mPaint)
    }
}