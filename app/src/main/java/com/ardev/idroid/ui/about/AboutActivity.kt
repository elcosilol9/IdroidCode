package com.ardev.idroid.ui.about

import android.content.Context
import android.os.Bundle
import com.google.android.material.transition.MaterialSharedAxis
import com.ardev.idroid.R
import com.ardev.idroid.common.base.*
import com.ardev.idroid.common.ext.*

class AboutActivity : BaseActivity<ActivityAboutBinding>(ActivityAboutBinding::inflate) {
	private val viewModel by viewModels<AboutViewModel>
	private val listAboutAdapter: ProjectsAdapter by lazy { ProjectsAdapter() }
	
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding {
			aaListAbout.adapter = listAboutAdapter
      		aaListAbout.setHasFixedSize(true)
       }
    }

	
}