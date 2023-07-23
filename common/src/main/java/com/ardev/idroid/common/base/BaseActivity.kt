package com.ardev.idroid.common.base

import android.os.*
import java.lang.ref.WeakReference
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.viewbinding.ViewBinding
import com.ardev.idroid.app.AppActivity

abstract class BaseActivity<VB : ViewBinding>(val inflate: (LayoutInflater) -> VB) : AppActivity() {

	val binding: VB by lazy { inflate(layoutInflater) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preSetContent()
        setContentView(binding.root)
        observeViewModel()
        onBackPressedDispatcher.addCallback(this, OnBackPressedCallbackInner(this))
    }
    
    
 internal class OnBackPressedCallbackInner(baseActivity: BaseActivity<*>) : OnBackPressedCallback(true) {
    private val activity: WeakReference<BaseActivity<*>> = WeakReference(baseActivity)

    override fun handleOnBackPressed() {
        activity.get()?.onBackEvent() ?: return
    }
}

	
	open fun onBackEvent() {
		finish()
	}
	
	protected open fun preSetContent() {}
	protected open fun observeViewModel() {}

	inline fun <VB : ViewBinding> BaseActivity<VB>.binding(block: VB.() -> Unit) {
    	binding.apply(block)
	}
}