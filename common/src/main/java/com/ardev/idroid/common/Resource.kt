package com.ardev.idroid.common

sealed class Resource <out R> {
    data class Success<out T>(val data: T): Resource<T>()
    data class Error(val message: String): Resource<Nothing>()
    object Loading : Resource<Nothing>()
}