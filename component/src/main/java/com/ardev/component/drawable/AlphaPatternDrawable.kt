package com.ardev.component.drawable

import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable

/**
 * Este drawable dibujará un patrón de tablero de ajedrez simple blanco y gris.
 * Es el patrón que a menudo verá como fondo detrás de una imagen parcialmente transparente en muchas aplicaciones.
 */
class AlphaPatternDrawable(private val rectangleSize: Int) : Drawable() {

    private val paint = Paint()
    private val paintWhite = Paint()
    private val paintGray = Paint()

    private var numRectanglesHorizontal: Int = 0
    private var numRectanglesVertical: Int = 0

    /**
     * Bitmap en el que se almacenará en caché el patrón.
     * Esto se hace para que el patrón no tenga que recrearse cada vez que se llama a draw ().
     * Debido a que recrear el patrón es bastante costoso. Solo se recreará si cambia el tamaño.
     */
    private var bitmap: Bitmap? = null

    init {
        paintWhite.color = -0xFFF1F1F1
        paintGray.color = -0xFFCDCDCD
    }

    override fun draw(canvas: Canvas) {
        if (bitmap != null && !bitmap!!.isRecycled) {
            canvas.drawBitmap(bitmap!!, null, bounds, paint)
        }
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

    override fun setAlpha(alpha: Int) {
        throw UnsupportedOperationException("Alpha is not supported by this drawable.")
    }

    override fun setColorFilter(cf: ColorFilter?) {
        throw UnsupportedOperationException("ColorFilter is not supported by this drawable.")
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        val height = bounds.height()
        val width = bounds.width()
        numRectanglesHorizontal = Math.ceil((width / rectangleSize).toDouble()).toInt()
        numRectanglesVertical = Math.ceil(height / rectangleSize.toDouble()).toInt()
        generatePatternBitmap()
    }

    /**
     * Esto generará un bitmap con el patrón tan grande como el rectángulo en el que se nos permitió dibujar.
     * Hacemos esto para almacenar en caché el bitmap para que no tengamos que recrearlo cada vez que se llame a draw (),
     * ya que tarda unos pocos milisegundos.
     */
    private fun generatePatternBitmap() {
        if (bounds.width() <= 0 || bounds.height() <= 0) {
            return
        }

        bitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), Config.ARGB_8888)
        val canvas = Canvas(bitmap!!)

        val r = Rect()
        var verticalStartWhite = true
        for (i in 0..numRectanglesVertical) {
            var isWhite = verticalStartWhite
            for (j in 0..numRectanglesHorizontal) {
                r.top = i * rectangleSize
                r.left = j * rectangleSize
                r.bottom = r.top + rectangleSize
                r.right = r.left + rectangleSize
                canvas.drawRect(r, if (isWhite) paintWhite else paintGray)
                isWhite = !isWhite
            }
            verticalStartWhite = !verticalStartWhite
        }
    }
}