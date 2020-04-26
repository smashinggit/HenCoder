package com.cs.view.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.cs.view.R
import com.cs.view.util.dp2px

class CameraView : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)


    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBitmap = getAvatar(400)
    private val mCamera = Camera()

    init {
        mPaint.textSize = dp2px(26f)
        mCamera.rotateX(30f)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //上半部分
        canvas.save()
        canvas.translate(100f + 400 / 2, 100f + 400 / 2)
        canvas.clipRect(-(400f / 2), -(400f / 2), 400f / 2, 0f)
        canvas.translate(-(100f + 400 / 2), -(100f + 400 / 2))
        canvas.drawBitmap(mBitmap, 100f, 100f, mPaint)
        canvas.restore()

        //下半部分
        canvas.save()
        canvas.translate(100f + 400 / 2, 100f + 400 / 2)
        mCamera.applyToCanvas(canvas)
        canvas.clipRect(-(400f / 2), 0f, 400f / 2, 400f / 2)
        canvas.translate(-(100f + 400 / 2), -(100f + 400 / 2))
        canvas.drawBitmap(mBitmap, 100f, 100f, mPaint)
        canvas.restore()
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