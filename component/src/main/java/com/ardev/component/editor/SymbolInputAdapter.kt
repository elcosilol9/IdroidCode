package com.ardev.component.editor

import android.view.ViewGroup
import android.view.View
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView
import com.ardev.idroid.databinding.LayoutSymbolItemBinding
import com.ardev.idroid.common.ext.*
import com.ardev.idroid.common.*

class SymbolInputAdapter(private val editor: CodeEditor) :
    ListAdapter<Symbol, SymbolHolder>(DiffCallback<Symbol>()) {

       private val pairs = listOf('}', ')', ']', '"', '\'', '>')
 
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SymbolHolder(
        parent bind LayoutSymbolItemBinding::inflate)
    
    override fun onBindViewHolder(holder: SymbolHolder, position: Int)  = holder.bind(currentList[position])
    
   inner class SymbolHolder(val binding: LayoutSymbolItemBinding) : ViewHolder(binding.root) {
        fun bind(symbolItem: Symbol) = with(binding) {
         symbol.text = symbolItem.label
         root.setOnClickListener {
            insertSymbol(symbolItem.commit, symbolItem.offset)
             }
        }
    }
    
    private fun insertSymbol(text: String, selectionOffset: Int) {
        val cur = editor.text.cursor
        if (selectionOffset < 0 || selectionOffset > text.length) {
            return
        }
        if (cur.isSelected) {
            editor.text.delete(
                cur.leftLine,
                cur.leftColumn,
                cur.rightLine,
                cur.rightColumn
            )
            editor.notifyIMEExternalCursorChange()
        }
        if (cur.leftColumn < editor.text.getColumnCount(cur.leftLine) &&
            text.length == 1 &&
            text[0] == editor.text[cur.leftLine, cur.leftColumn] &&
            pairs.contains(text[0])
        ) {
            editor.moveSelectionRight()
        } else {
            editor.commitText(text)
            if (selectionOffset != text.length) {
                editor.setSelection(
                    cur.rightLine,
                    cur.rightColumn - (text.length - selectionOffset)
                )
            }
        }
    }

}