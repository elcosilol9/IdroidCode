package com.ardev.component.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.TypedValue
import android.widget.FrameLayout

class DefaultView(context: Context) : FrameLayout(context) {
    private var text = "null"
    private val minSize: Int
    private val paint: Paint

    init {
        setWillNotDraw(false)
        val padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, context.resources.displayMetrics).toInt()
        setPadding(padding, padding, padding, padding)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.typeface = Typeface.MONOSPACE
        paint.color = Color.GRAY
        paint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18f, context.resources.displayMetrics)
        minSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, context.resources.displayMetrics).toInt()
    }

    fun setDisplayText(cs: CharSequence?) {
        text = cs?.toString() ?: "null"
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val fontMetrics = paint.fontMetricsInt
        val dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val baseLineY = height / 2 + dy
        val baseLineX = (width - Math.min(width, paint.measureText(text).toInt())) / 2
        canvas.drawText(text, baseLineX.toFloat(), baseLineY.toFloat(), paint)
    }

    override fun dispatchDraw(canvas: Canvas) {
      
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)

        if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.max(minSize, bounds.width() + paddingLeft + paddingRight)
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.max(minSize, bounds.height() + paddingTop + paddingBottom)
        }
        setMeasuredDimension(width, height)
    }
}