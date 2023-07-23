package com.ardev.idroid.ui.main.fragment.editor

import io.github.rosemoe.sora.lang.EmptyLanguage
import io.github.rosemoe.sora.lang.Language
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import org.eclipse.tm4e.core.registry.IGrammarSource
import org.eclipse.tm4e.core.registry.IThemeSource
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme
import com.ardev.idroid.app.IdroidApplication
import java.io.File
import android.view.Menu
import java.io.InputStreamReader


fun CodeEditorFragment.getColorScheme(): TextMateColorScheme {
        try {
            var themeSource: IThemeSource
            if (IdroidApplication.isDarkMode(requireContext())) {
                themeSource =
                    IThemeSource.fromInputStream(
                        requireContext().assets.open("textmate/darcula.json"),
                        "darcula.json",
                        null
                    )
            } else {
                themeSource =
                    IThemeSource.fromInputStream(
                        requireContext().assets.open("textmate/QuietLight.tmTheme"),
                        "QuietLight.tmTheme",
                        null
                    )
            }
            return TextMateColorScheme.create(themeSource)
        } catch (e: Exception) {
            return TextMateColorScheme.create(IThemeSource.fromInputStream(
                        requireContext().assets.open("textmate/QuietLight.tmTheme"),
                        "QuietLight.tmTheme",
                        null
                    ))
        }
        
    }

fun CodeEditorFragment.getLanguage(tmLanguage: String, langConf: String): Language {
        try {
            return TextMateLanguage.create(
                IGrammarSource.fromInputStream(
                    requireContext().assets.open(tmLanguage),
                    File(tmLanguage).name,
                    null
                ),
                InputStreamReader(
                    requireContext().assets.open(langConf)
                ),
                getColorScheme().themeSource
            )
        } catch (e: Exception) {
            return EmptyLanguage()
        }
    }
    
    fun CodeEditorFragment.getSymbolsName(lang: String): Array<String> =  when (lang) {
	LANGUAGE_XML -> arrayOf("->",  "<",  ">", "/",  "=",  "\"",  ":",  "@",  "+",   "(",  ")",  ";",  ",", ".", "?", "|", "\\", "&", "!", "[", "]", "{", "}", "_", "-")
	else -> arrayOf("->", "{", "}", "(", ")", ",", ".", ";", "\"", "?", "+", "-", "*", "/")
	
	}
	fun CodeEditorFragment.getSymbols(lang: String): Array<String> =  when (lang) {
	LANGUAGE_XML -> arrayOf("\t",  "<",  ">", "/",  "=",  "\"",  ":",  "@",  "+",   "(",  ")",  ";",  ",", ".", "?", "|", "\\", "&", "!", "[", "]", "{", "}", "_", "-" )
	else -> arrayOf("\t", "{}", "}", "()", ")", ",", ".", ";", "\"", "?", "+", "-", "*", "/")
	
	}
    
    
   fun Menu.validateEditorMenu(): Boolean = this != null && this.findItem(2) != null  && this.findItem(3) != null
   
   
    
    