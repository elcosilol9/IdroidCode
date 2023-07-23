package com.ardev.idroid.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference
import com.ardev.idroid.common.ext.*

object MainViewModelProvider {
    private lateinit var ref: WeakReference<MainViewModel>

    fun init(viewModel: MainViewModel) {
        ref = WeakReference(viewModel)
    }

    fun getViewModel(): MainViewModel {
        if (ref.get() == null) restartApp() 
        return ref.get()!!
    }
}

