package com.ardev.idroid.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardev.idroid.ui.main.fragment.editor.CodeEditorFragment
import com.ardev.idroid.ui.main.fragment.preview.*
import com.ardev.idroid.ext.*
import com.ardev.idroid.ui.main.model.EditorModel
import java.io.File
import java.net.URL
import java.net.URLConnection
import java.util.*

class PageAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    private val data = ArrayList<EditorModel>()

    fun submitList(files: List<EditorModel>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = data.size
            override fun getNewListSize(): Int = files.size
            
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return Objects.equals(data[oldItemPosition], files[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return Objects.equals(data[oldItemPosition], files[newItemPosition])
            }
        })
        data.clear()
        data.addAll(files)
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = data.size
    

    override fun getItemId(position: Int): Long {
        return if (data.isEmpty()) -1 else data[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        data.forEach { d ->
            if (d.hashCode().toLong() == itemId) return true
            }
        return false
    }

    
    override fun createFragment(position: Int): Fragment {
        lateinit var  fragment: Fragment
        if (getFile(position).isXMLFile) {
            if (data[position].preview) {
                if (getFile(position).isResourceXMLFileInDir("values")) {
                    fragment = ValuesFragment.newInstance(getFile(position).path)
                } else if (getFile(position).isResourceXMLFileInDir("layout")) {
                    fragment = LayoutFragment.newInstance(getFile(position).path)
                } else if (getFile(position).isResourceXMLFileInDir("drawable")) {
                    fragment = ImageFragment.newInstance(getFile(position).path)
                }
            } else {
                fragment = CodeEditorFragment.newInstance(getFile(position).path)
            }
        } else if (getFile(position).isImage) {
            fragment = ImageFragment.newInstance(getFile(position).path)
        } else {
            fragment = CodeEditorFragment.newInstance(getFile(position).path)
        }
        return fragment
    }

    private fun getFile(position: Int) = data[position].file
 
}