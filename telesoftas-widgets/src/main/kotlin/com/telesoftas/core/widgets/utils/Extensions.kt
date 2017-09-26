package com.telesoftas.core.widgets.utils

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet

fun AttributeSet.obtainAttributes(
        context: Context,
        styleable: IntArray,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0,
        action: (TypedArray) -> Unit
) {
    var typedArray: TypedArray? = null
    try {
        val theme = context.theme
        typedArray = theme.obtainStyledAttributes(this, styleable, defStyleAttr, defStyleRes)
        action.invoke(typedArray)
    } finally {
        typedArray?.recycle()
    }
}
