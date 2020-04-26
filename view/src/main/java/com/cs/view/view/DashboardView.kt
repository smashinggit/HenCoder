package com.cs.view.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.cs.view.util.dp2px

/**
 * 自定义仪表盘
 *
 *
 * PathDashPathEffect:
 * 可以将路径转变成指定的小形状，比如 矩形
 *
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DashboardView : View {
    private val RADIUS = dp2px(150f)
    private val ANGLE = 120
    private val LENGTH = dp2px(100f) //指针长度

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private lateinit var pathDashPathEffect: PathDashPathEffect
    private val arcPath = Path()  //弧的路径
    private val dashPath = Path()  //刻度的路径


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = dp2px(2f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        arcPath.addArc(
            width / 2 - RADIUS,
            height / 2 - RADIUS,
            width / 2 + RADIUS,
            height / 2 + RADIUS,
            (90 + ANGLE / 2).toFloat(),
            (360 - ANGLE).toFloat()
        )

        //每一个刻度即一个小矩形
        val dashRect = RectF(0f, 0f, dp2px(2f), dp2px(10f))
        dashPath.addRect(dashRect, Path.Direction.CW)

        val pathMeasure = PathMeasure(arcPath, false)
        val dashSpacing = (pathMeasure.length - dashRect.width()) / 20  //两个刻度之间的距离 = 弧长 / 刻度数

        pathDashPathEffect =
            PathDashPathEffect(
                dashPath,
                dashSpacing,
                0f,
                PathDashPathEffect.Style.ROTATE
            )
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //画线
        canvas.drawPath(arcPath, mPaint)

        //画刻度
        mPaint.pathEffect = pathDashPathEffect
        canvas.drawArc(
            width / 2 - RADIUS,
            height / 2 - RADIUS,
            width / 2 + RADIUS,
            height / 2 + RADIUS,
            (90 + ANGLE / 2).toFloat(),
            (360 - ANGLE).toFloat(),
            false, mPaint
        )

        mPaint.pathEffect = null


        //画指针
        canvas.drawLine(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (Math.cos(Math.toRadians(getAngleFormMark(5))) * LENGTH + width / 2).toFloat(),
            (Math.sin(Math.toRadians((getAngleFormMark(5)))) * LENGTH + height / 2).toFloat(),
            mPaint
        )
    }


    private fun getAngleFormMark(mark: Int): Double {
        return (90 + ANGLE / 2 + (360 - ANGLE) / 20 * mark).toDouble()
    }
}