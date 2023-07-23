package com.ardev.component.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.ardev.idroid.common.ext.*
import com.ardev.component.R

class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var message: String? = null
    private var image: Int = 0
	private var showImage: Boolean = true
	
    private val imageiv: ImageView
    private val messagetv: TextView

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.EmptyView, defStyleAttr, 0)

        try {
            message = typedArray.getString(R.styleable.EmptyView_message)
            image = typedArray.getResourceId(R.styleable.EmptyView_image, 0)
            showImage = typedArray.getBoolean(R.styleable.EmptyView_showImage, true)
        } finally {
            typedArray.recycle()
        }

        imageiv = ImageView(context)
        imageiv.id = View.generateViewId() 
		messagetv = TextView(context)
        messagetv.id = View.generateViewId() 
        
        imageiv.setImageResource(if(image != 0) image else R.drawable.ic_empty_view)
        imageiv.visibility = if (showImage) View.VISIBLE else View.GONE
        imageiv.layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            startToStart = ConstraintSet.PARENT_ID
            endToEnd = ConstraintSet.PARENT_ID
            topToTop = ConstraintSet.PARENT_ID 
            bottomToTop = messagetv.id 
            topMargin = 16f.px
        }

        
        messagetv.text = message ?: resources.getString(R.string.empty_view_msg)
        messagetv.setTextColor(ContextCompat.getColor(context, R.color.secondaryVariant))
        messagetv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        messagetv.layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            startToStart = ConstraintSet.PARENT_ID 
            endToEnd = ConstraintSet.PARENT_ID 
            topToTop = imageiv.id
            bottomToBottom = ConstraintSet.PARENT_ID 
            topMargin = 16f.px
        }

        addView(imageiv)
        addView(messagetv)
    }

    
}