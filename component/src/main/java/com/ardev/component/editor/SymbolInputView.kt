package com.ardev.component.editor

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SymbolInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    fun bindEditor(editor: CodeEditor) {
        adapter = SymbolInputAdapter(editor)           
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun setSymbols(vararg symbols: Symbol) {
            adapter.submitList(symbols.toList())      
    }       
}