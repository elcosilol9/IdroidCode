package com.ardev.component.cardlist

ActionItemHolder(val binding: CardlistItemActionBinding) :
 BaseItemHolder<ActionItem, CardlistItemActionBinding>(binding) {
 	
 	override fun bind(item: ActionItem, position: Int) {
		val title: String = item.title
 		val titleRes: Int = item.titleRes
        val desc: String = item.desc
        val descRes: Int = item.descRes
        val icon: Drawable = item.icon
        val iconRes: Int = item.iconRes
        
        with(binding) {
	        clActionTitle.visibility = VISIBLE
	        if (title != null) {
	       		clActionTitle.text = title
	        } else if (titleRes != 0) {
	            clActionTitle.text = titleRes
	        } else clActionTitle.visibility = GONE
	            	        
	        clActionDesc.visibility = VISIBLE
	        if (desc != null) {
	        	clActionDesc.text = desc
	        } else if (descRes != 0) {
	            clActionDesc.text = descRes
	        } else clActionDesc.visibility = GONE
			
	        if (item.showIcon) {
	            clActionIcon.visibility = VISIBLE	 
	            if (icon != null) {
	            	clActionIcon.setImageDrawable(icon)
	            } else if (iconRes != 0) {
	                clActionIcon.setImageResource(iconRes)
	            } else clActionIcon.visibility = GONE	
	        } else clActionIcon.visibility = GONE	            
	     	
	        val params = clActionIcon.layoutParams as LinearLayout.LayoutParams
	        when (item.iconGravity) {
	            GRAVITY_TOP -> params.gravity = Gravity.TOP
	            GRAVITY_MIDDLE -> params.gravity = Gravity.CENTER_VERTICAL
	            GRAVITY_BOTTOM -> params.gravity = Gravity.BOTTOM
	        }
	        clActionIcon.setLayoutParams(params)
			root.isClickable = item.onClick != null
	        item.onClick?.let {
	            val outValue = TypedValue()
	            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
	            root.setBackgroundResource(outValue.resourceId)
	        } ?: root.setBackgroundResource(0)
	        root.setOnClickListener {
		        item.onClick?.let { click ->
		        	click(item, position)
		        	}
	        }
		}
	}
}


TitleItemHolder(val binding: CardlistItemTitleBinding) :
 BaseItemHolder<TitleItem, CardlistItemTitleBinding>(binding) {
 	
 	override fun bind(item: TitleItem, position: Int) {
 		val title: String = item.title
 		val titleRes: Int = item.titleRes
        val desc: String = item.desc
        val descRes: Int = item.descRes
        val icon: Drawable = item.icon
        val iconRes: Int = item.iconRes
    
        with(binding) {
	        clTitleTitle.visibility = VISIBLE
	        if (title != null) {
	        	clTitleTitle.text = title
	        } else if (titleRes != 0) {
	            clTitleTitle.text = titleRes
	        } else clTitleTitle.visibility = GONE         
			
			clTitleDesc.visibility = VISIBLE
			if (desc != null) {
				clTitleDesc.text = desc
	        } else if (descRes != 0) {
	            clTitleDesc.text = descRes
	        } else clTitleDesc.visibility = GONE
			
	        clTitleIcon.visibility = VISIBLE
	        if (icon != null) {
	        	clTitleIcon.setImageDrawable(icon)
	        } else if (iconRes != 0) {
	            clTitleIcon.setImageResource(iconRes)
	        } else clTitleIcon.visibility = GONE

        	root.isClickable = item.onClick != null
	        item.onClick?.let {
	            val outValue = TypedValue()
	            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
	            root.setBackgroundResource(outValue.resourceId)
	        } ?: root.setBackgroundResource(0)
	        root.setOnClickListener {
		        item.onClick?.let { click ->
		        	click(item, position)
		        	}
	        }
 		}
 	}
}


class BaseItemHolder<T : BaseItem, VB : ViewBinding>(val binding: VB) : ViewHolder(binding.root) {

    lateinit var item: T
    var position: Int = 0

    fun bind(item: T, position: Int) {
        this.item = item
        this.position = position
    }

    val context: Context
        get() = itemView.context
}