package com.ardev.idroid.ui.projects.adapter

import android.view.ViewGroup
import android.view.View
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ardev.idroid.databinding.ItemShimmerProjectsBinding
import icom.ardev.idroid.common.ext.*


class ShimmerProjectsAdapter(val size: Int = randInt(5, 10)) : Adapter<ShimmerProjectsAdapter.ShimmerProjectsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  ShimmerProjectsHolder(
    parent bind ItemShimmerProjectsBinding::inflate
    )
        
    override fun onBindViewHolder(holder: ShimmerProjectsHolder, position: Int) = holder.bind()
    
    override fun getItemCount(): Int = size

    inner class ShimmerProjectsHolder(binding: ItemShimmerProjectsBinding) : ViewHolder(binding.root) {
    	fun bind() = with(binding) {
    			root.startShimmer()
    			ispName.layoutParams as ConstraintLayout.LayoutParams.apply {
					width = randInt(160, 200)
				}.also {
					ispName.layoutParams = it 
				} 	
				
				ispPkgName.layoutParams as ConstraintLayout.LayoutParams.apply {
					width = randInt(240, 300)
				}.also {
					ispPkgName.layoutParams = it 
				} 	
    			
    		}   	
    
    }
    
}
    
    

