package com.ardev.component.view.colorpicker.components.hsl

import androidx.core.graphics.ColorUtils
import com.ardev.component.view.colorpicker.Metrics
import com.ardev.component.view.colorpicker.Paints
import com.ardev.component.view.colorpicker.components.ArcComponent

internal class LightnessComponent(metrics: Metrics, paints: Paints, arcLength: Float, arcStartAngle: Float) : ArcComponent(metrics, paints, arcLength, arcStartAngle) {

    override val componentIndex: Int = 2
    override val range: Float = 1f
    override val noOfColors = 11 // TODO 3 should be sufficient
    override val colors = IntArray(noOfColors)
    override val colorPosition = FloatArray(noOfColors)

    override fun getColorArray(color: FloatArray): IntArray {
        for (i in 0 until noOfColors) {
            color[componentIndex] = i.toFloat() / (noOfColors - 1)
            colors[i] = ColorUtils.HSLToColor(color)
        }
        return colors
    }
}