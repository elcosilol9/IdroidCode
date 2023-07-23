package com.ardev.idroid.ui.home.wizard

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import java.io.File
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.shape.CornerFamily
import com.ardev.idroid.databinding.WizardTemplateItemBinding
import com.ardev.idroid.common.ext.*
import com.ardev.idroid.common.*

class WizardTemplateAdapter: ListAdapter<Template, WizardTemplateHolder>(
AsyncDifferConfig.Builder(DiffCallback<Template>()).build()
    ) {

	 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): = WizardTemplateHolder(
            parent bind WizardTemplateItemBinding::inflate)
     
	override fun onBindViewHolder(holder: WizardTemplateHolder, position: Int) = holder.bind(currentList[position])

    
inner class WizardTemplateHolder(binding: WizardTemplateItemBinding) : ViewHolder(binding.root) {
        fun bind(template: Template) = with(binding) {
         	templateName.text = template.name
             val iconFile =  File(template.path, "icon.png")
			if (iconFile.exists()) templateIcon.loadImage(iconFile, 8)
            root.setOnClickListener {
              onItemClick?.let { click ->
               click(template)
               }
            }
            
        }
    }

	protected var onItemClick : ((Template) -> Unit)? = null

    fun setOnItemClick(listener: (Template) -> Unit) {
        onItemClick = listener
        }    
   }