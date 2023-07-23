package com.ardev.idroid.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.ardev.idroid.ui.main.model.Editable

class MainViewModel
constructor(
    private var editorRepository: EditorRepository
) : ViewModel() {

    private val _user = MutableStateFlow(Resource.Loading())
    val user: StateFlow<Resource<Boolean>> = _user
    
     private val _listEditor = MutableStateFlow<List<Editable>>(ArrayList())
    val listEditor: StateFlow<List<Editable>> = _listEditor
    
    init {
    _listEditor.value = editorRepository.getListEditor().onEach {it}.launchIn(viewModelScope)
    
    
    }
   
    
    
  }