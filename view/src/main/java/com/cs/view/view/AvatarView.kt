package com.cs.view.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.cs.view.R
import com.cs.view.util.dp2px

class AvatarView : View {

    private val WIDTH = dp2px(300f)
    private val PADDING = dp2px(50f)

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mAvatar = getAvatar(WIDTH.toInt())
    private val porterDuff = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    private val savedArea = RectF(PADDING, PADDING, WIDTH + PADDING, WIDTH + PADDING)

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //绘制黑边
        canvas.drawOval(
            savedArea.left - dp2px(5f),
            savedArea.top - dp2px(5f),
            savedArea.right + dp2px(5f),
            savedArea.bottom + dp2px(5f),
            mPaint
        )

        val saveLayer = canvas.saveLayer(savedArea, mPaint)
        canvas.drawOval(savedArea, mPaint)
        mPaint.xfermode = porterDuff
        canvas.drawBitmap(mAvatar, PADDING, PADDING, mPaint)

        mPaint.xfermode = null
        canvas.restoreToCount(saveLayer)
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