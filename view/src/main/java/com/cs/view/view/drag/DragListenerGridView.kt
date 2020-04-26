package com.cs.view.view.drag

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat.setTranslationY
import androidx.core.view.ViewCompat.setTranslationX
import android.R.layout
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class DragListenerGridView : ViewGroup {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val COLUMNS = 2
    private val ROWS = 3

    val viewConfiguration = ViewConfiguration.get(context)

    lateinit var draggedView: View
    var orderedChildren = ArrayList<View>()

    init {
        isChildrenDrawingOrderEnabled = true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onFinishInflate() {
        super.onFinishInflate()

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            orderedChildren.add(child)

            child.setOnLongClickListener {
                draggedView = it
                it.startDragAndDrop(null, DragShadowBuilder(it), it, 0)
            }
            child.setOnDragListener(DragListener())
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(specWidth, specHeight)

        val childWidth = specWidth / COLUMNS
        val childHeight = specHeight / ROWS

        measureChildren(
            MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY)
            , MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
//        var childLeft = 0
//        var childTop = 0
//        val childWidth = measuredWidth / COLUMNS
//        val childHeight = measuredHeight / ROWS
//
//        for (i in 0 until childCount) {
//            val child = getChildAt(i)
//
//            if (i % COLUMNS == 0) {
//                child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
//
//            } else {
//                childLeft += childWidth //向左移动一个子view的宽度
//                child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
//
//                childLeft = 0            //重新开始一行，左边重置为0
//                childTop += childHeight  //向下移动一个子view的高度
//            }
//        }

        val count = childCount
        var childLeft: Int
        var childTop: Int
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS
        for (index in 0 until count) {
            val child = getChildAt(index)
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight
            child.layout(0, 0, childWidth, childHeight)
            child.translationX = childLeft.toFloat()
            child.translationY = childTop.toFloat()
        }
    }

    inner class DragListener : OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {

            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    if (event.localState == v) {
                        v.visibility = View.INVISIBLE
                    }
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    if (event.localState != v) {
                        sort(v)
                    }
                }
                DragEvent.ACTION_DRAG_EXITED -> {

                }
                DragEvent.ACTION_DRAG_LOCATION -> {

                }
                DragEvent.ACTION_DROP -> {

                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    if (event.localState == v) {
                        v.visibility = View.VISIBLE
                    }
                }
            }
            return true
        }

    }


    private fun sort(targetView: View) {
        var draggedIndex = -1
        var targetIndex = -1
        for (i in 0 until childCount) {
            val child = orderedChildren[i]
            if (targetView === child) {
                targetIndex = i
            } else if (draggedView === child) {
                draggedIndex = i
            }
        }
        if (targetIndex < draggedIndex) {
            orderedChildren.removeAt(draggedIndex)
            orderedChildren.add(targetIndex, draggedView)
        } else if (targetIndex > draggedIndex) {
            orderedChildren.removeAt(draggedIndex)
            orderedChildren.add(targetIndex, draggedView)
        }
        var childLeft: Int
        var childTop: Int
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS
        for (index in 0 until childCount) {
            val child = orderedChildren[index]
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight
            child.animate()
                .translationX(childLeft.toFloat())
                .translationY(childTop.toFloat()).duration = 150
        }
    }

}