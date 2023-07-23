package com.ardev.idroid.common.ext

import kotlinx.coroutines.*

    @JvmStatic
    fun inParallel(runnable: Runnable) = CoroutineScope(Dispatchers.IO).launch {
        runnable.run()
    }
    
    fun doInBg(block: suspend () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            block()
        }
    }
