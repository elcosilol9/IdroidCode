package com.ardev.idroid.ui.main


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Activity
import androidx.activity.viewModels
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import com.ardev.component.widget.breadcrumbs.model.IBreadcrumbItem
import com.ardev.idroid.ext.JsonUtils
import com.ardev.idroid.service.IndexServiceConnection
import com.ardev.idroid.service.IndexService
import com.ardev.idroid.ui.home.ProjectProvider
import androidx.annotation.NonNull
import com.ardev.builder.project.Project
import com.ardev.idroid.ext.ProjectUtils
import com.ardev.idroid.ui.home.ProjectManager
import com.google.android.material.transition.MaterialFade
import android.view.ViewGroup
import androidx.transition.TransitionManager
import com.ardev.idroid.ui.main.model.Editable
import com.ardev.idroid.ui.main.adapter.PageAdapter
import com.ardev.idroid.databinding.ActivityMainBinding
import com.ardev.idroid.ui.main.adapter.FileListAdapter
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.ardev.idroid.ext.*
import com.ardev.idroid.app.MainViewModelProvider
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import android.view.animation.AnticipateInterpolator
import androidx.core.graphics.Insets
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ardev.component.widget.breadcrumbs.BreadcrumbsView
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.reflect.Type
import com.ardev.idroid.base.BaseActivity
import com.google.gson.Gson
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.ArrayList
import java.util.List
import java.util.Arrays
import org.apache.commons.io.FileUtils

import java.util.concurrent.Executors
import com.google.android.material.appbar.MaterialToolbar
import java.io.File
import androidx.viewpager2.widget.CompositePageTransformer
import com.ardev.component.widget.breadcrumbs.DefaultBreadcrumbsCallback
import com.google.android.material.tabs.TabLayout
import com.ardev.component.widget.breadcrumbs.model.BreadcrumbItem
import com.ardev.idroid.R

class MainActivity(): BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), ProjectManager.OnProjectOpenListener {
	
	
private lateinit var mFilesAdapter: FileListAdapter 
private lateinit var mPagerAdapter: PageAdapter 
private lateinit var project: Project
private lateinit var mProjectManager: ProjectManager
private lateinit var mIndexServiceConnection: mIndexServiceConnection

var firstTimeInitTab = true
var firstTimeInitList = true

	
	val viewModel = MainViewModel by viewModels
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

	override fun onCreate(bundle: Bundle) {
		super.onCreate(bundle)
	MainViewModelProvider.init(viewModel)
	
	 project = ProjectProvider.getProject()
	 mProjectManager = ProjectManager.getInstance()
     mProjectManager.addOnProjectOpenListener(this@MainActivity)
	  mIndexServiceConnection = IndexServiceConnection(viewModel)
    
		initView()
		viewModel.setFatherPath(project.getRootFile().path)
		
	if (!project.equals(mProjectManager.getCurrentProject())) openProject(project)
	}
	
	private fun initView() {
	with(binding) {
navView.addSystemWindowInsetToPadding(false, false, false, false)
pager2.addSystemWindowInsetToMargin(false, false, false, false)
pager2.addInsetTypeImeToPadding()

	setSupportActionBar(toolbar)
	supportActionBar.setDisplayHomeAsUpEnabled(true)
	
		val toggle = ActionBarDrawerToggle(this@MainActivity, drawer, toolbar, R.string.app_name, R.string.app_name)
		toggle.syncState()
		toggle.setDrawerSlideAnimationEnabled(true)
		drawer.addDrawerListener(toggle)
		
		breadcrumbsView.setCallback { item, position -> 
		viewModel.setCurrentPath(getPath(position))
		 bkpListBreadcrumb(breadcrumbsView.getItems())
		
	}
}
		initRecyclerView()
		initTab()
		}
		
		private fun initRecyclerView(){
	with(binding) {
mFilesAdapter = FileListAdapter()
	with(list) {
 	adapter = mFilesAdapter
	setHasFixedSize(true)
	setItemAnimator(DefaultItemAnimator())
	}
mFilesAdapter.setOnClickListener { view, file -> 
			 if (file.isDirectory()) {
  	viewModel.addBreadcrumbItem(file.path)
	viewModel.setCurrentPath(file.path)
		} else {
	viewModel.addEditor(Editable(file, false))
drawer.closeDrawer(GravityCompat.START)
}
}

mFilesAdapter.setOnLongClickListener { v, file -> 

if (file.isDirectory()) showFolderOptionsMenu(v, file)
else if((ProjectUtils.isLayoutXMLFile(file) || ProjectUtils.isValuesXMLFile(file)  || ProjectUtils.isDrawableXMLFile(file)) && file.isFile()) showFileOptionsMenu(v, file)

}
 }
 
 
 }
 
		override fun observeViewModel() {
 	
 	viewModel.fatherPath.observe(this) { fatherPath -> 
       	supportActionBar.setTitle(File(fatherPath).name)
       		
      val breadcrumbsCache = File("${fatherPath}/app/build/.cache", ".breadcrumbs")
      
if (breadcrumbsCache.exists()) {
	try {
  val listBreadCrumb: ArrayList<BreadcrumbItem> =  JsonUtils.jsonToList(breadcrumbsCache.readText(), BreadcrumbItem::class.java)
   listBreadCrumb.forEach{binding.breadcrumbsView.addItem(it)}

	} catch (e: Exception) {}
		
		} else {
	
	binding.breadcrumbsView.addItem(BreadcrumbItem.createSimpleItem(File(fatherPath).name))
	
	}
            }
            
	viewModel.currentPath.observe(this) { path -> 
	var _path: String = null
	if(firstTimeInitList) {
                firstTimeInitList = false
     val currentPathCache = File("${viewModel.fatherPath.value}/app/build/.cache", ".currentpath")
if (currentPathCache.exists()) {
	try {
   _path =  Gson().fromJson(currentPathCache.readText(), String::class.java)
   
	} catch (e: Exception) {} 
	} else {
	_path = path
	}
	} else {
	_path = path
	}
	showList(_path)
	bkpCurrentPath(_path)
	}
	
	
	
	
	viewModel.breadcrumbItem.observe(this) { file -> 
		val breadcrumbItem = BreadcrumbItem(viewModel.listDir().value)
		breadcrumbItem.setSelectedItem(file.name)
  binding.breadcrumbsView.addItem(breadcrumbItem)
  bkpListBreadcrumb(binding.breadcrumbsView.getItems())
	}
		
	viewModel.listEditor
                .observe(this) { files -> 
                
                    mPagerAdapter.submitList(files)
                    binding.tab.setVisibility( if(files.isEmpty()) View.GONE else View.VISIBLE)
                }
                
   viewModel.currentTabPosition
                .observe(this) { position -> 
                if(firstTimeInitTab) {
                firstTimeInitTab = false
                 val positionFile = File("${viewModel.fatherPath.value}/app/build/.cache", ".position")
if (positionFile.exists()) {
	try {
  val pos: Int = Gson().fromJson(positionFile.readText(), Int::class.java)
  binding.pager2.setCurrentItem(pos, false)
		} catch (e: Exception) {} 
		} else {
			 binding.pager2.setCurrentItem(position, false)
			}
              } else {
                
             binding.pager2.setCurrentItem(position, false)
				
               }
                 }
                 
    viewModel.isIndexing
                .observe(this) { indexing -> 
                   
                }
                
        viewModel.currentState
                .observe(this) { message -> 
                supportActionBar.setSubtitle(message) 
                
                }
		}
		
private fun getPath(depth: Int): String {
	if (depth == -1) depth = binding.breadcrumbsView.getItems().size() - 1
	val sbPath =  StringBuffer(viewModel.fatherPath.value)
	for (i in 1..depth) {
		
		sbPath.append("/").append(v.breadcrumbsView.getItems().get(i).getSelectedItem())
	}
	return sbPath.toString()
}
 
 private fun showList(path: String ) {
   
    Executors.newSingleThreadExecutor().execute {
    
       val list = File(path).listFiles().toList()
         
       runOnUiThread{
     
	
                    
            if (list.isEmpty()) {
                binding.scrollingView.setVisibility(View.GONE)
                binding.emptyItem.setVisibility(View.VISIBLE)
            } else {
                binding.scrollingView.setVisibility(View.VISIBLE)
                binding.emptyItem.setVisibility(View.GONE)
                 mFilesAdapter.setList(list)
        viewModel.setListCurrent(list)
        }
        
            
	
	}
	}
	}

private fun showFolderOptionsMenu(view: View, file: File) {
   
    val chars: Array<CharSequence> = arrayOf("Compartir", "Borrar")
	
	val dialog = MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_MaterialAlertDialog_NoRounded)
		 
		dialog.setItems(chars) { d, item -> 

			 
		 }
		 dialog.show()
        
    }
private fun showFileOptionsMenu(view: View, file: File) {
   
     val chars: Array<CharSequence> = arrayOf("Vista Previa", "Editar")
    
		val dialog = MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_MaterialAlertDialog_NoRounded)
		 
		dialog.setItems(chars) { d, item -> 
		when (item) {
		 0 -> {
		viewModel.addEditor(Editable(file, true))
		binding.drawer.closeDrawer(GravityCompat.START)
			}
		1 -> {
		viewModel.addEditor(Editable(file, false))
		binding.drawer.closeDrawer(GravityCompat.START)
			}
		}
			 
		 }
		 dialog.show()
        
    }
    
   private fun showTabMenu(tab: TabLayout.Tab) {
   val editable: Editable = viewModel.listEditor.value.get(tab.getPosition())
  val isPreview = editable.isPreview()
   
   val menu =  PopupMenu(this, tab.view)
	with(menu.getMenu()) {
		add("Cerrar")
		add("Cerrar otros")
		add("Cerrar todos")
		}
		
		menu.setOnMenuItemClickListener{ item -> 
			 when (item.getTitle().toString()) {

	 "Cerrar" -> viewModel.closeEditor(editable)
	"Cerrar otros" -> viewModel.closeOthersEditor(editable)
	"Cerrar todos" -> viewModel.closeAllEditor()
						
					 }
					 
					 true
				}
				menu.show()
   
   
   }
    
    
    
    private fun initTab() {
    
    
		
   mPagerAdapter = PageAdapter(supportFragmentManager, lifecycle)
        binding.pager2.setAdapter(mPagerAdapter)
        binding.pager2.setUserInputEnabled(false)
		binding.pager2.setPageTransformer { view, position -> 
		view.setAlpha(0.2f)
		view.setVisibility(View.VISIBLE)
		view.animate().alpha(1.0f).setDuration(view.getResources().getInteger(android.R.integer.config_shortAnimTime))
		}
        
        v.tab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener() {
            override fun onTabUnselected(tab: TabLayout.Tab) {
                
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
           showTabMenu(tab)
               
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
               viewModel.setCurrentTabPosition(tab.getPosition())

            }
        })
		
       TabLayoutMediator(binding.tab, binding.pager2, true, false) { tab, i -> 
			val isPreview = viewModel.getListEditor().value.get(i).isPreview()

			val fileName = viewModel.getListEditor().value.get(i).getFile().name
			
			tab.setText(fileName)
        	if(isPreview) tab.setIcon(R.drawable.ic_preview)
        	 
        	
        }.attach()
    
    
    
    }
    
    
     fun openProject(project: Project) {
        mIndexServiceConnection.setProject(project)
        viewModel.setIndexing(true)
        val intent = Intent(this@MainActivity, IndexService::class.java)
        startService(intent)
        bindService(intent, mIndexServiceConnection, Context.BIND_IMPORTANT)
    }
    
     override fun onProjectOpen(project: Project) {
    
    
    }
    
    
   override fun onBackPressed() {
             if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START)
            } else {
            super.onBackPressed()
            }
	}
	
	

	  private fun bkpListBreadcrumb(bList: List<IBreadcrumbItem>) {
   try {
        val bciFile = File("${viewModel.fatherPath.value}/app/build/.cache", ".breadcrumbs")
         val content  = GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson( bList, TypeToken<List<BreadcrumbItem>>(){}.type)
        
        if(!bciFile.exists()) bciFile.createNewFile()
		if(!bList.isEmpty()){
       bciFile.writeText(content)
                 }
                 } catch (e: Exception) {}
   
   }
   
   private fun bkpCurrentPath(path: String) {
   try {
         val pathFile =  File("${viewModel.fatherPath.value}/app/build/.cache", ".currentpath")
        
        if(!pathFile.exists()) pathFile.createNewFile()
		pathFile.writeText(Gson().toJson(path))
       
                 } catch (e: Exception) {}
   
   
         }
	
	

	}