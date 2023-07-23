package com.ardev.idroid.common.base

import android.view.MenuInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.viewbinding.ViewBinding
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle


abstract class BaseFragment<VB : ViewBinding>(
    val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {
    lateinit var binding: VB
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       
		requireActivity().addMenuProvider( object: MenuProvider {
				override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
					menu.clear()
					createMenu(menu)
 				}
				override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        
     observeViewModel() 
    

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    	if (!::binding.isInitialized) {
      		binding = inflate.invoke(inflater, container, false)
      	}
        return binding.root  
    }
	
	protected open fun createMenu(menu: Menu) {}
	protected open fun observeViewModel() {}
	
	inline fun <VB : ViewBinding> BaseFragment<VB>.binding(block: VB.() -> Unit) {
    	binding.apply(block)
	}
}