package com.ardev.api.actions

import java.util.HashMap

class ActionData {
    private val data = HashMap<Class<*>, Any>()

    fun <T> put(type: Class<T>, obj: T?) {
        requireNotNull(type)
        data[type] = obj as Any
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(type: Class<T>): T? {
        requireNotNull(type)
        val obj = data[type] ?: return null
        return type.cast(obj)
    }

    fun clear() {
        data.clear()
    }
}