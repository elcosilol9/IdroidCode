package com.ardev.idroid.common

import androidx.recyclerview.widget.DiffUtil

class DiffCallback<T>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem == newItem
    override fun areContentsTheSame(oldItem: T, newItem: T) =  oldItem == newItem
}