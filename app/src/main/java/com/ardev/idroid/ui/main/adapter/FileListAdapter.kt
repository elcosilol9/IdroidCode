package com.ardev.idroid.ui.main.adapter

import android.view.*
import java.io.File
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.*
import com.ardev.idroid.databinding.ItemFilelistBinding
import com.ardev.idroid.common.ext.*
import com.ardev.idroid.common.*

class FileListAdapter : ListAdapter<File, ListHolder>(
AsyncDifferConfig.Builder(DiffCallback<File>()).build()
    ) {
   
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListHolder( 
    parent bind ItemFilelistBinding::inflate)
    

    override fun onBindViewHolder(holder: ListHolder, position: Int) = holder.bind(currentList[position])

    inner class ListHolder(private val binding: ItemFilelistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(file: File) {
            with(binding) {
                name.text = file.name
                icon.setScaleType(ImageView.ScaleType.CENTER_CROP)
                icon.loadIcon(file)
                root.setOnClickListener { onItemClick?.invoke(file) }
                root.setOnLongClickListener {
                onItemLongClick?.invoke(file)
              true
            }
                
            }
        }
    }
    protected var onClickListener: ((File) -> Unit)? = null
    protected var onItemLongClick : ((File) -> Unit)? = null

    fun setOnItemClick(listener: (File) -> Unit){
        onItemClick = listener
    }
    fun setOnItemLongClick(listener: (File) -> Unit){
        onItemLongClick = listener
    }

}