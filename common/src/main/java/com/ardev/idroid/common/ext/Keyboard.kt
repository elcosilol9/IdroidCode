package com.ardev.idroid.common.ext

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.ardev.idroid.app.IdroidApplication

object Keyboard {
    private const val TAG_ON_GLOBAL_LAYOUT_LISTENER = -8

 
    fun registerSoftInputChangedListener(activity: Activity, block: (Int) -> Unit) {
        val flags = activity.window.attributes.flags 
        if (flags and WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS != 0) { 
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        } 
        
        val contentView = activity.findViewById<View>(android.R.id.content) as FrameLayout
        val decorViewInvisibleHeightPre = intArrayOf(getDecorViewInvisibleHeight(activity.window)) 
        val onGlobalLayoutListener = OnGlobalLayoutListener {
        val height = getDecorViewInvisibleHeight(activity.window)
        if (decorViewInvisibleHeightPre[0] != height) { 
        block(height)
        decorViewInvisibleHeightPre[0] = height
    		 }
		  } 
        contentView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
        contentView.setTag(TAG_ON_GLOBAL_LAYOUT_LISTENER, onGlobalLayoutListener)
    }

    fun unregisterSoftInputChangedListener(activity: Activity) {
        val contentView = activity.findViewById<View>(android.R.id.content) as FrameLayout
        val onGlobalLayoutListener = contentView.getTag(TAG_ON_GLOBAL_LAYOUT_LISTENER) as? OnGlobalLayoutListener
        onGlobalLayoutListener?.let {
            contentView.viewTreeObserver.removeOnGlobalLayoutListener(it)
            contentView.setTag(TAG_ON_GLOBAL_LAYOUT_LISTENER, null)
        }
    }
    
  }

    fun Fragment.showSoftInput() {
        if (isSoftInputVisible.not()) toggleSoftInput()
    }

   val Fragment.isSoftInputVisible get() = getDecorViewInvisibleHeight(activity.window) > 0

    private fun toggleSoftInput() {
        val imm = IdroidApplication.instance.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm.toggleSoftInput(0, 0)
    }
    
	private var sDecorViewDelta = 0
	private fun getDecorViewInvisibleHeight(window: Window): Int {
     val decorView = window.decorView
     val outRect = Rect()
     decorView.getWindowVisibleDisplayFrame(outRect)
val delta = abs(decorView.bottom - outRect.bottom)
if (delta <= navBarHeight + statusBarHeight) {
    sDecorViewDelta = delta
    return 0
	}
return delta - sDecorViewDelta
	}