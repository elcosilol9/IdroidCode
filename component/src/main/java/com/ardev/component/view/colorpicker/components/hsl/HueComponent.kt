package com.ardev.component.view.colorpicker.components.hsl

import androidx.core.graphics.ColorUtils
import com.ardev.component.view.colorpicker.Metrics
import com.ardev.component.view.colorpicker.Paints
import com.ardev.component.view.colorpicker.components.ArcComponent

internal class HueComponent(metrics: Metrics, paints: Paints, arcLength: Float, arcStartAngle: Float) : ArcComponent(metrics, paints, arcLength, arcStartAngle) {

    override val componentIndex = 0
    override val range: Float = 360f
    override val noOfColors = 360
    override val colors = IntArray(noOfColors)
    override val colorPosition = FloatArray(noOfColors)

    override fun getColorArray(color: FloatArray): IntArray {
        color[1] = 1f
        color[2] = 0.5f
        for (i in 0 until noOfColors) {
            color[componentIndex] = i.toFloat()
            colors[i] = ColorUtils.HSLToColor(color)
        }
        return colors
    }
}
