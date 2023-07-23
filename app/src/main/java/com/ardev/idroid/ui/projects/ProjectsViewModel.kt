package com.ardev.idroid.ui.projects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProjectsViewModel() : ViewModel() {
   private val repository: ProjectsRepository lazy { ProjectsRepository() }
    
    private val _loadProjects: MutableLiveData<Resource<List<Project>>> = MutableLiveData()
    val loadProjects: LiveData<Resource<List<Project>>>
        get() = _loadProjects
              
    private val _openProject: MutableLiveData<Resource<Project>> = MutableLiveData()
    val openProject: LiveData<Resource<Project>>
        get() = _openProject
        
       init {
        loadProjects(true)
        }
        
    fun loadProjects(await: Boolean = false) {
        viewModelScope.launch {
          repository.loadProjects(await).onEach {
            _loadProjects.value = it
            }.launchIn(viewModelScope)  
        }
    }
       
    fun openProject(project: Project, await: Boolean = false) {
        viewModelScope.launch {
          repository.openProject(project, await).onEach {
            _openProject.value = it
            }.launchIn(viewModelScope)  
        }
    }
    
}