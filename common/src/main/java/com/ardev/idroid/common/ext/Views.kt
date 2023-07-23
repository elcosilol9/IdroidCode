package com.ardev.idroid.common.ext

import android.content.*
import androidx.core.view.*
import android.widget.*
import android.view.*
import androidx.annotation.*
import com.google.android.material.snackbar.Snackbar
import com.ardev.idroid.app.IdroidApplication
import com.ardev.idroid.app.IdroidApplication.Companion.context as appContext

fun TextView.setTextIfDifferent(data: String) {
    if (this.text.toString() != data) {
        text = data
    }

}


val String.toast: Unit
	get() = Toast.makeText(appContext, this, Toast.LENGTH_SHORT).show()

fun String.showSnackBar(view: View) =
    Snackbar.make(view, this, Snackbar.LENGTH_SHORT)
        .apply {
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
        }.show()

inline fun View.popupMenu(block: (PopupMenu) -> Unit) {
    val popup = PopupMenu(context, this)
    block(popup)
    popup.show()
}

inline val View.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(context)
    
inline infix fun <VB : ViewBinding> ViewGroup.bind(
    crossinline bindingInflater: LayoutInflater.(parent: ViewGroup, attachToParent: Boolean) -> VB
): VB = LayoutInflater.from(context).let {
    bindingInflater.invoke(it, this, false)
}

fun View.setMargins(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    val params = (layoutParams as? ViewGroup.MarginLayoutParams)
    params?.setMargins(
        left ?: params.leftMargin,
        top ?: params.topMargin,
        right ?: params.rightMargin,
        bottom ?: params.bottomMargin)
    layoutParams = params
}

@JvmOverloads
fun View.addSystemWindowInsetToPadding(
    left: Boolean = false,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false
) {
    val (initialLeft, initialTop, initialRight, initialBottom) =
        listOf(paddingLeft, paddingTop, paddingRight, paddingBottom)

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.updatePadding(
            left = initialLeft + (if (left) insets.left else 0),
            top = initialTop + (if (top) insets.top else 0),
            right = initialRight + (if (right) insets.right else 0),
            bottom = initialBottom + (if (bottom) insets.bottom else 0)
        )
        windowInsets
    }
}

@JvmOverloads
fun View.addSystemWindowInsetToMargin(
    left: Boolean = false,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false
) {
    val (initialLeft, initialTop, initialRight, initialBottom) =
        listOf(marginLeft, marginTop, marginRight, marginBottom)

    ViewCompat.setOnApplyWindowInsetsListener(this) {  view, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            updateMargins(
                left = initialLeft + (if (left) insets.left else 0),
                top = initialTop + (if (top) insets.top else 0),
                right = initialRight + (if (right) insets.right else 0),
                bottom = initialBottom + (if (bottom) insets.bottom else 0)
            )
        }
        windowInsets
    }
}
@JvmOverloads
fun View.addInsetTypeImeToPadding() {
ViewCompat.setOnApplyWindowInsetsListener(this) { view, windowInsets -> 
                    val imeVisible = windowInsets.isVisible(WindowInsetsCompat.Type.ime())
                    val insets  = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                   view.setPadding(0, 0, 0, insets.bottom)
                    if (imeVisible) {
                        val imeHeight = windowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                       view.setPadding(0, 0, 0, imeHeight)
                    }
                    windowInsets
        }

}