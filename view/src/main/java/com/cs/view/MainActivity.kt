package com.cs.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cs.view.activity.*
import com.cs.view.view.TagLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDragUpDown.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    DragHelperUpDownActivity::class.java
                )
            )
        }
        btnDragToCollect.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    DragToCollectActivity::class.java
                )
            )
        }
        btnDrawer.setOnClickListener { startActivity(Intent(this, DrawerActivity::class.java)) }
        btnDragHelper.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    DragHelperGridViewActivity::class.java
                )
            )
        }
        btnDragListener.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    DragListenerGridViewActivity::class.java
                )
            )
        }
        btnTwoPage.setOnClickListener { startActivity(Intent(this, TwoPageActivity::class.java)) }
        btnMaterialTextView.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MaterialTextViewActivity::class.java
                )
            )
        }
        btnScalableImageView.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ScalableImageViewActivity::class.java
                )
            )
        }
        btnTagLayout.setOnClickListener { startActivity(Intent(this, TagLayout::class.java)) }
    }
}
