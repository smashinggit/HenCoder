package com.cs.view.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import com.cs.view.util.dp2px

class TagLayout : ViewGroup {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val LINE_HEIGHT = dp2px(10f) //行间距
    private val MARGIN = dp2px(10f) //左右间距
    private val childrenBounds = arrayListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthUsed = 0     //整个layout最终的宽度，即最宽行的宽度
        var heightUsed = 0    //整个layout最终的高度
        var lineWidthUsed = 0  //每一行使用过的宽度
        var lineMaxHeight = 0  //每一行的最大高度
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            measureChildWithMargins(
                child,
                widthMeasureSpec,
                0,  //原本应该填lineWidthUsed，但是这样的话会导致每行最后一个子view被挤压，所以填0，让子view能够测量出正常宽高
                heightMeasureSpec,
                heightUsed
            )

            if (specWidthMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + child.measuredWidth > specWidth) { //如果宽度超过specWidth,应该换行
                heightUsed += lineMaxHeight + LINE_HEIGHT.toInt() //换行
                lineWidthUsed = 0
                lineMaxHeight = 0

                measureChildWithMargins(
                    child,
                    widthMeasureSpec,
                    0,
                    heightMeasureSpec,
                    heightUsed
                )
            }

            if (childrenBounds.size <= i) {
                val rect = Rect()
                childrenBounds.add(rect)
            }


            //每一行的子view需要加上 MARGIN
            lineWidthUsed += MARGIN.toInt()

            childrenBounds[i].set(
                lineWidthUsed,
                heightUsed,
                lineWidthUsed + child.measuredWidth,
                heightUsed + child.measuredHeight
            )

            //一个子view测量完成之后, 每一行的使用过的宽度做累加
            lineWidthUsed += child.measuredWidth

            widthUsed = Math.max(widthUsed, lineWidthUsed)   //更新最大宽度
            lineMaxHeight = Math.max(lineMaxHeight, child.measuredHeight)  //更新该行的最大高度
        }

        setMeasuredDimension(widthUsed, heightUsed + lineMaxHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childBounds = childrenBounds[i]
            val layoutParams = child.layoutParams as MarginLayoutParams

            child.layout(
                childBounds.left,
                childBounds.top,
                childBounds.right,
                childBounds.bottom
            )
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}