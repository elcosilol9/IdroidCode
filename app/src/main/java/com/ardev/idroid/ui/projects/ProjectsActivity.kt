package com.ardev.idroid.ui.projects

import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import com.ardev.idroid.ui.projects.adapter.*

class ProjectsActivity : BaseActivity<ActivityProjectsBinding>(ActivityProjectsBinding::inflate) {

    private lateinit var viewModel: ProjectsViewModel by viewModels
    private val listAdapter: ProjectsAdapter by lazy { ProjectsAdapter() }
    
    override fun preSetContent() {
        installSplashScreen().setOnExitAnimationListener {
            it.remove()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
           
        setupRecyclerView()
        setupFab()    
    }
    
    private fun setupRecyclerView() {
        binding.apList.adapter = listAdapter
        binding.apList.setHasFixedSize(true)
        listAdapter.setOnItemClick { project ->
            viewModel.openProject(project)
        }
        binding.apShimmers.adapter = ShimmerProjectsAdapter()
        binding.apShimmers.setHasFixedSize(true)
    }
    
    private fun setupFab() {
        binding.apFab.addSystemWindowInsetToMargin(bottom = true)
        binding.apFab.setOnClickListener {
        if (binding.apLoadingContainer.visibility == View.GONE) {
            WizardFragment.newInstance().apply {
            setOnProjectCreated { project ->
              viewModel.loadProjects()
              viewModel.openProject(project, true) 
            }
            }.also {
             supportFragmentManager.showFragment(it)
            }
          }
        }
    }

    override fun observeViewModel() {
       viewModel.loadProjects.observe(viewLifecycleOwner) { state ->     
            when(state) {
                is Resource.Success -> {
                   if (state.data.isNullOrEmpty()) {
                    manageViewVisibility(UiState.ERROR)
                      } else {
                    manageViewVisibility(UiState.SUCCESS)
                    listAdapter.submitList(state.data)
                      }
                }
                is Resource.Error -> {
                manageViewVisibility(UiState.ERROR)
                }
                is Resource.Loading -> {
                    manageViewVisibility(UiState.LOADING)
                }
                else -> Unit
            }
        }
        
        viewModel.openProject.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Resource.Success -> {
                ProjectProvider.init(state.data)
                  launchActivity<MainActivity>()
                }
                is Resource.Error -> {
                Toast.makeText(this@ProjectsActivity, "Failed to open project.", Toast.LENGHT_SHORT).show()
                }
                else -> Unit
            }
        }
           
    }
   
    private fun manageViewVisibility(currentState: UiState) {
        when (currentState) {
            UiState.LOADING -> {
               binding.apEmptyView.visibility = View.GONE
               binding.apListContainer.visibility = View.GONE
               binding.apLoadingContainer.visibility = View.VISIBLE
            }
            UiState.SUCCESS -> {
               binding.apLoadingContainer.visibility = View.GONE
               binding.apEmptyView.visibility = View.GONE
               binding.apListContainer.visibility = View.VISIBLE 
            }
            UiState.ERROR -> {
               binding.apLoadingContainer.visibility = View.GONE
               binding.apListContainer.visibility = View.GONE
               binding.apEmptyView.visibility = View.VISIBLE
            }
        }        
    }
    
    override fun onBackEvent() {
    	finish()
    }
    
}