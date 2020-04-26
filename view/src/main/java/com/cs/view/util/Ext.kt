package com.cs.view.util

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast


fun Context.dp2px(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    )
}

fun View.dp2px(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    )
}

fun Context.log(msg: String) {
    Log.e("tag", "$msg")
}

fun View.log(msg: String) {
    Log.e("tag", "$msg")
}

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun View.toast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
