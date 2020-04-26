package com.cs.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cs.view.R
import com.cs.view.util.toast
import kotlinx.android.synthetic.main.activity_drawer.*

class DrawerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)
        btnOpen.setOnClickListener {
            drawer.openDrawer()
            toast("sss")
        }
        btnClose.setOnClickListener {
            drawer.closeDrawer()
        }

    }
}