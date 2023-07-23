package com.ardev.idroid.common.ext

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.List
import java.util.ArrayList

inline fun <reified T> String.toBean(): T = Gson().fromJson(this, T::class.java)

inline fun <reified T:Any> T.toJson(): String = Gson().toJson(this,T::class.java)

inline fun <reified T> String.toList(): List<T> {
    val typeToken = TypeToken.getParameterized(ArrayList::class.java, T::class.java).type    
    return Gson().fromJson(this, typeToken) ?: ArrayList()
}