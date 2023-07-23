package com.ardev.idroid.ui.main.fragment.editor

import android.graphics.Typeface
import android.view.MenuItem
import androidx.fragment.app.activityViewModels
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.MenuProvider
import androidx.core.view.MenuHost
import androidx.core.view.forEach
import android.view.MenuInflater
import android.view.Menu
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import androidx.annotation.MenuRes
import com.ardev.tools.formatter.CodeFormatter
import com.ardev.tools.formatter.JavaFormatter
import androidx.lifecycle.Lifecycle
import android.content.Context
import com.google.android.material.appbar.MaterialToolbar
import io.github.rosemoe.sora.event.*
import io.github.rosemoe.sora.lang.Language
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.elevation.SurfaceColors
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme
import io.github.rosemoe.sora.widget.CodeEditor
import com.ardev.idroid.ext.isAlive
import io.github.rosemoe.sora.lang.EmptyLanguage
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import com.ardev.idroid.ext.ProjectUtils
import com.ardev.idroid.ui.main.model.Editable
import com.ardev.idroid.app.MainViewModelProvider
import com.ardev.idroid.ui.main.MainViewModel
import com.ardev.idroid.ui.main.MainActivity
import android.os.Bundle
import android.graphics.Color
import androidx.fragment.app.Fragment
import java.io.InputStreamReader
import java.io.IOException
import java.io.File
import java.io.FileReader
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.ardev.idroid.ext.KeyboardUtils as Keyboard
import io.github.rosemoe.sora.widget.ArSymbolInputView
import java.nio.charset.StandardCharsets
import org.apache.commons.io.FileUtils
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import org.eclipse.tm4e.core.registry.IGrammarSource
import org.eclipse.tm4e.core.registry.IThemeSource
import com.ardev.idroid.app.IdroidApplication
import com.ardev.idroid.R


class CodeEditorFragment : Fragment() {
	
	private lateinit var _activity: MainActivity
	private lateinit var mainViewModel: MainViewModel 
  private lateinit var currentFile: File
	private lateinit var symbol: ArSymbolInputView 
	private lateinit var editor: CodeEditor 
	private lateinit var view: View 
	private lateinit var viewRoot: ViewGroup
	private lateinit var menu_undo: MenuItem
	private lateinit var menu_redo: MenuItem
	
	override fun onAttach(context: Context) {
		super.onAttach(context)
	_activity = context as MainActivity
	  Keyboard.registerSoftInputChangedListener(_activity) {onSoftInputChanged()}
	}
	
	override fun onDetach() {
		super.onDetach()
		clearCodeEditorMenu()
	}
	
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
		mainViewModel = MainViewModelProvider.getViewModel()
        currentFile = File(getPath())
         
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       	view = inflater.inflate(R.layout.fragment_code_editor, container, false)
		
		
		viewRoot = view.findViewById(R.id.root) as ViewGroup
		editor = view.findViewById(R.id.editor)
		symbol = view.findViewById(R.id.symbol)
       
       
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
  
    configureEditor(editor)
    
    
     if (currentFile.exists()) {
            try {
                readContent()
                
            } catch (e: Exception) { }
              
             setEditorLanguage(currentFile.extension)
            
           
        }
        
        mainViewModel.isIndexing()
                .observe(viewLifecycleOwner, { indexing -> 
                
                editor.setEditable(!indexing)
                val check = !indexing
               val _menu: Menu? = mainViewModel.getMenu().getValue() as Menu?
				if(_menu != null)
              _menu.forEach{ it.isEnabled = check }
                
                }
                
        )
        
        var menuHost: MenuHost = requireActivity()
		menuHost.addMenuProvider( object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            
            
            menu.clear()
          // menuHost.invalidateMenu()
            mainViewModel.setMenu(menu)
            
  	menu.add("Guardar").setOnMenuItemClickListener {
    saveText()
true
    }
       
       
      if(ProjectUtils.isXMLFile(currentFile) || ProjectUtils.isJavaFile(currentFile)){
    menu.add(0, 4, 0, "Formato").setOnMenuItemClickListener {
   val formaterr = when (currentFile.extension) {
   "xml" -> CodeFormatter.xmlFormat(editor.getText().toString())
	"java" -> JavaFormatter(editor.getText().toString()).format()
	else -> null
    }
    editor.setText(formaterr)
	true
    }
    }
    if(ProjectUtils.isLayoutXMLFile(currentFile) || ProjectUtils.isValuesXMLFile(currentFile)  || ProjectUtils.isDrawableXMLFile(currentFile)) {

   menu.add(0, 5, 0, "Vista previa").setOnMenuItemClickListener {
	saveText()
	mainViewModel.addEditor(Editable(currentFile, true))

	true
	}
   }
                
               
                
            }

override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        
       // updateCodeEditorMenu()
        
    }

	 override fun onPause() {
        super.onPause()
		saveText()
		clearCodeEditorMenu()
}


	 override fun onResume() {
		super.onResume()
		readContent()
		//updateCodeEditorMenu()
}
	
	
  

    override fun onDestroy() {
        super.onDestroy()
       editor.release()
    }
    
    fun configureEditor(editor: CodeEditor) {
        editor.apply {
        setTypefaceText(ResourcesCompat.getFont(requireContext(), R.font.jetbrains_mono_regular))
        setTextSize(12F)
        setEdgeEffectColor(Color.TRANSPARENT)
        setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO)
		setLigatureEnabled(true)
		setNonPrintablePaintingFlags(CodeEditor.FLAG_DRAW_WHITESPACE_LEADING)
		setInputType(EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS or EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE or EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
		
	subscribeEvent(ContentChangeEvent::class.java) {  _, _ -> 
	postDelayed(
                    ::updateCodeEditorMenu,
                    50
                )
	}
               
   }
		
			
					
			symbol.apply {
			setTypefaceText(ResourcesCompat.getFont(requireContext(), R.font.jetbrains_mono_regular))
			bindEditor(editor)
			}
        var props = editor.getProps().apply {
            overScrollEnabled = false
            allowFullscreen = false
            deleteEmptyLineFast = false
			useICULibToSelectWords = true
			symbolPairAutoCompletion = true
			deleteMultiSpaces = -1
			
        }
        	
        	
        	
    }

   

     fun setEditorLanguage(lang: String) {
        when (lang) {
            LANGUAGE_JAVA -> editor.setEditorLanguage(getLanguage("textmate/java/syntaxes/java.tmLanguage.json", "textmate/java/language-configuration.json"))
            LANGUAGE_KOTLIN -> editor.setEditorLanguage(getLanguage("textmate/kotlin/syntaxes/kotlin.tmLanguage", "textmate/kotlin/language-configuration.json"))
             LANGUAGE_XML -> editor.setEditorLanguage(getLanguage("textmate/xml/syntaxes/xml.tmLanguage.json", "textmate/xml/language-configuration.json"))
			 LANGUAGE_JSON -> editor.setEditorLanguage(getLanguage("textmate/json/syntaxes/json.tmLanguage.json", "textmate/json/language-configuration.json"))
			 LANGUAGE_GROOVY -> editor.setEditorLanguage(getLanguage("textmate/groovy/syntaxes/groovy.tmLanguage","textmate/groovy/language-configuration.json"))
            else -> editor.setEditorLanguage(EmptyLanguage())
        }
        editor.setColorScheme(getColorScheme())
		symbol.addSymbols(getSymbolsName(lang), getSymbols(lang))
    }
    
    
    	
	
    
    	
 fun readContent() {
		editor.setText(File(getPath()).readText())
		}
				
	
fun saveText(text: String = editor.getText().toString()) {
	File(getPath()).writeText(text)
	mainViewModel.savedText(true)
                }
	
	private fun onSoftInputChanged() {
        
        if (Keyboard.isSoftInputVisible(_activity)) {
            TransitionManager.beginDelayedTransition(viewRoot, Slide(Gravity.BOTTOM))
            symbol.visibility = View.VISIBLE
            addCodeEditorMenu()
            updateCodeEditorMenu()
        } else {
            TransitionManager.beginDelayedTransition(viewRoot, Slide(Gravity.TOP))
            symbol.visibility = View.GONE
            clearCodeEditorMenu()
         }
    }
    
    
     private fun undo() {
        if (editor.canUndo()) editor.undo()
    }

    private fun redo() {
        if (editor.canRedo()) editor.redo()
    }
    
    private fun addCodeEditorMenu() {
     val menu = mainViewModel.getMenu().getValue() as Menu
	clearCodeEditorMenu()
     	
     menu.add(0, 2, 0 , "Deshacer")?.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)?.setIcon(R.drawable.ic_undo)?.setOnMenuItemClickListener {
        undo()
        true
        }
        
       menu.add(0, 3, 0, "Rehacer")?.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)?.setIcon(R.drawable.ic_redo)?.setOnMenuItemClickListener {
        redo()
        true
        }
 
    }
    
    private fun updateCodeEditorMenu() {
    val menu = mainViewModel.getMenu().getValue() as Menu
		if(menu.validateEditorMenu()){
        menu.findItem(2)?.isEnabled = editor.canUndo()
        menu.findItem(3)?.isEnabled = editor.canRedo()
    }
   }
	
	private fun clearCodeEditorMenu() {
	 val menu = mainViewModel.getMenu().getValue() as Menu
		if(menu.validateEditorMenu()) {
	menu?.removeItem(2)
	menu?.removeItem(3)
	}
	}
	
    fun getPath(): String = arguments!!.getString("path", "")

    
    companion object {
        const val LANGUAGE_JAVA = "java"
          const val LANGUAGE_KOTLIN = "kt"
        const val LANGUAGE_XML = "xml"
          const val LANGUAGE_JSON = "json"
          const val LANGUAGE_GROOVY = "gradle"
           
        const val TAG = "CodeEditorFragment"

        fun newInstance(path: String): CodeEditorFragment {
            val args: Bundle = Bundle()
            args.putString("path", path)
            val fragment = CodeEditorFragment()
            fragment.arguments = args
            return fragment
        }
    }
    
    
    }