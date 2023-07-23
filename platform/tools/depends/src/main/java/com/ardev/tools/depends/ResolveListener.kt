package com.ardev.tools.depends

interface ResolveListener {
        fun onResolve(message: String)
        fun onFailure(message: String)
    }
