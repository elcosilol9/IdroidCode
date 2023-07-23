package com.ardev.idroid.common.ext


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.FragmentTransaction

fun Fragment.isAlive(): Boolean {
    return activity != null && !isDetached && isAdded && !isRemoving && view != null
}

fun FragmentManager.showFragment(fragment: Fragment, containerId: Int = android.R.id.content, backStackTag: String? = null) {
    this.commit {
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        add(containerId, fragment)
        addToBackStack(backStackTag) 
    }
}