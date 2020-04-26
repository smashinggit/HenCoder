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

class ImageTextView : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)


    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBitmap = getAvatar(dp2px(200f).toInt())

    init {
        mPaint.textSize = dp2px(26f)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(dp2px(100f),dp2px(100f))
        canvas.rotate(45f,dp2px(100f),dp2px(100f))
        canvas.drawBitmap(mBitmap, 0f, 0f, mPaint)
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