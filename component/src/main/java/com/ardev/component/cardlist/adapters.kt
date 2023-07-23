package com.ardev.component.cardlist

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import androidx.recyclerview.widget.ListAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import com.ardev.component.databinding.CardlistListCardBinding
import com.ardev.idroid.common.ext.*
import com.ardev.idroid.common.*

import com.google.android.material.card.MaterialCardView
import java.util.ArrayList
import java.util.List
import java.util.UUID

class CardListAdapter : ListAdapter<CardModel, CardListHolder>(
AsyncDifferConfig.Builder(DiffCallback<CardModel>()).build()
    ) {
	 
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CardListHolder(
    parent bind CardlistListCardBinding::inflate
    )       
    

    override fun onBindViewHolder(holder: CardListHolder, position: Int) = holder.bind(currentList[position])  	
    	     
    inner class CardListHolder(binding: CardlistListCardBinding) : ViewHolder(binding.root) {      

        fun bind(card: CardModel) {  
        	val title = card.title
        	val titleColor = card.titleColor
        	val cardColor = card.cardColor
        	with(binding) {         
            	clListChild.setNestedScrollingEnabled(false)
            	if (title != 0) {
		        	clListTitle.visibility = VISIBLE
		            clListTitle.text = title
		            clListTitle.textColor = titleColor
		        } else clListTitle.visibility = GONE
		        
		        if (cardColor != 0) {
		        clListCard.setCardBackgroundColor(cardColor)
		        }
            }
            card.customAdapter?.let {
            	setupCustomAdapter(it)
            } ?: setupCardItemAdapter(card)
        }

        private fun setupCardItemAdapter(card: CardModel) {           
               val adapter = CardItemAdapter()
                binding.clListChild.setRecycledViewPool(RecycledViewPool())
                binding.clListChild.adapter = adapter
                adapter.submitList(card.items)           
        }

        private fun setupCustomAdapter(adapter: RecyclerView.Adapter<*>) {                   
                binding.clListChild.setRecycledViewPool(null)
                binding.clListChild.adapter = adapter          
        }
    }
}


class CardItemAdapter : ListAdapter<BaseItem, BaseItemHolder>(
AsyncDifferConfig.Builder(DiffCallback<BaseItem>()).build()
    ) {
	 
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(viewType) {  
	    is VIEW_TYPE_TITLE_ITEM {
	    	TitleItemHolder(
	    	parent bind CardlistItemTitleBinding::inflate
	    	)
	    }
	    is VIEW_TYPE_ACTION_ITEM {
	    	ActionItemHolder(
	    	parent bind CardlistItemActionBinding::inflate
	    	)
	    }
	    else -> throw IllegalArgumentException("Invalid view type")        
    }

    override fun onBindViewHolder(holder: BaseItemHolder, position: Int) = holder.bind(currentList[position], position)  	
    
	override fun getItemViewType(position: Int): Int {
	  return when(getItem(position)) {
		  is TitleItem -> VIEW_TYPE_TITLE_ITEM
		  is ActionItem -> VIEW_TYPE_ACTION_ITEM
		  else -> throw IllegalArgumentException("Invalid item type")
	  }	
	}
		
	companion object {
		private const val VIEW_TYPE_TITLE_ITEM = 0 
		private const val VIEW_TYPE_ACTION_ITEM = 1
	}
}