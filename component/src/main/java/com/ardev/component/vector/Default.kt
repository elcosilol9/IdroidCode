package com.ardev.component.vector

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path

object Default {

    val PATH_ATTRIBUTES = arrayOf(
        "name",
        "fillAlpha",
        "fillColor",
        "fillType",
        "pathData",
        "strokeAlpha",
        "strokeColor",
        "strokeLineCap",
        "strokeLineJoin",
        "strokeMiterLimit",
        "strokeWidth"
    )

    const val PATH_FILL_COLOR = Color.TRANSPARENT
    const val PATH_STROKE_COLOR = Color.TRANSPARENT
    const val PATH_STROKE_WIDTH = 0.0f
    const val PATH_STROKE_ALPHA = 1.0f
    const val PATH_FILL_ALPHA = 1.0f
    val PATH_STROKE_LINE_CAP = Paint.Cap.BUTT
    val PATH_STROKE_LINE_JOIN = Paint.Join.MITER
    const val PATH_STROKE_MITER_LIMIT = 4.0f
    const val PATH_STROKE_RATIO = 1.0f
    val PATH_FILL_TYPE = Path.FillType.WINDING
    const val PATH_TRIM_PATH_START = 0.0f
    const val PATH_TRIM_PATH_END = 1.0f
    const val PATH_TRIM_PATH_OFFSET = 0.0f

    const val VECTOR_VIEWPORT_WIDTH = 0.0f
    const val VECTOR_VIEWPORT_HEIGHT = 0.0f
    const val VECTOR_WIDTH = 0.0f
    const val VECTOR_HEIGHT = 0.0f
    const val VECTOR_ALPHA = 1.0f

    const val GROUP_ROTATION = 0.0f
    const val GROUP_PIVOT_X = 0.0f
    const val GROUP_PIVOT_Y = 0.0f
    const val GROUP_SCALE_X = 1.0f
    const val GROUP_SCALE_Y = 1.0f
    const val GROUP_TRANSLATE_X = 0.0f
    const val GROUP_TRANSLATE_Y = 0.0f
}