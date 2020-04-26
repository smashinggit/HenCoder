package com.cs.view.view.drag

import android.content.ClipData
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.cs.view.R
import com.cs.view.util.toast

class DragToCollectLayout : RelativeLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)


    lateinit var avatarView: ImageView
    lateinit var logoView: ImageView
    lateinit var collectorLayout: LinearLayout

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onFinishInflate() {
        super.onFinishInflate()
        avatarView = findViewById(R.id.avatarView)
        logoView = findViewById(R.id.logoView)
        collectorLayout = findViewById(R.id.llCollector)

        avatarView.setOnLongClickListener {
            val clipData = ClipData.newPlainText("name", "avatarView")
            it.startDragAndDrop(clipData, DragShadowBuilder(it), null, 0)
        }

        logoView.setOnLongClickListener {
            val clipData = ClipData.newPlainText("name", "logoView")
            it.startDragAndDrop(clipData, DragShadowBuilder(it), null, 0)
        }

        collectorLayout.setOnDragListener(CollectListener())
    }

    inner class CollectListener : OnDragListener {

        override fun onDrag(v: View, event: DragEvent): Boolean {

            when (event.action) {

                DragEvent.ACTION_DROP -> {

                    if (v is LinearLayout) {
                        val textView = TextView(context)
                        textView.setTextColor(Color.WHITE)
                        textView.text = event.clipData.getItemAt(0).text
                        v.addView(textView)
                    }
                }
            }
            return true
        }
    }

}