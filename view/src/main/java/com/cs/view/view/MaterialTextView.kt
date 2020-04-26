package com.cs.view.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import com.cs.view.util.dp2px

class MaterialTextView : EditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val TEXT_SIZE = dp2px(16f)
    private val TEXT_MARGIN = dp2px(8f)
    private val TEXT_VERTICAL_OFFSET = dp2px(22f)
    private val TEXT_HORIZONTAL_OFFSET = dp2px(6f)
    private val TEXT_ANIMATION_OFFSET = dp2px(20f)

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var floatingLabelShown = false
    private var floatingLabelFraction = 0f


    fun setFloatingLabelFraction(floatHitFraction: Float) {
        this.floatingLabelFraction = floatHitFraction
        invalidate()
    }

    fun getFloatingLabelFraction(): Float {
        return this.floatingLabelFraction
    }

    init {
        mPaint.textSize = TEXT_SIZE

        setPadding(
            paddingLeft,
            (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(), paddingRight, paddingBottom
        )

        val animator =
            ObjectAnimator.ofFloat(
                this@MaterialTextView, "floatingLabelFraction",
                0f, 1f
            )

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (!floatingLabelShown && it.isNotEmpty()) { //显示
                        floatingLabelShown = true
                        animator.start()
                    } else if (floatingLabelShown && it.isEmpty()) { //隐藏
                        floatingLabelShown = false
                        animator.reverse()
                    }
                }
            }
        })
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.alpha = (255 * floatingLabelFraction).toInt()
        val extraOffset = TEXT_ANIMATION_OFFSET * (1 - floatingLabelFraction)
        canvas.drawText(
            hint.toString(),
            TEXT_HORIZONTAL_OFFSET,
            TEXT_VERTICAL_OFFSET + extraOffset,
            mPaint
        )
    }

}