package com.ardev.component.view

import android.content.Context
import android.content.Intent
import android.util.Base64
import android.util.AttributeSet
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.*
import java.net.URL
import java.util.regex.Pattern
import com.ardev.idroid.common.ext.*

class MarkDownView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {
	
	private val IMAGE_PATTERN = Pattern.compile("!\\[(.*)]\\((.*)\\)")
	
	init {
		layoutParams = ViewGroup.LayoutParams(
		              	  ViewGroup.LayoutParams.MATCH_PARENT,
		             	  ViewGroup.LayoutParams.MATCH_PARENT
		            	)
		webViewClient = object : WebViewClient() {
		override fun onPageFinished(view: WebView?, url: String?) {
			view?.evaluateJavascript(text, null)
		}
	
		override fun shouldOverrideUrlLoading(
			view: WebView?,
			request: WebResourceRequest?
		): Boolean {
			context.startActivity(Intent(Intent.ACTION_VIEW, request?.url))
			return true
			}
		}
		loadUrl("file:///android_asset/html/preview.html")
		settings.javaScriptEnabled = true
		settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
	}

	var text: String = ""
		set(value) {
			field = "preview('${escapeForText(imgToBase64(value))}')"
		}
		get() = field


	private fun escapeForText(mdText: String): String = mdText.replaces(
		"\n" to "\\\\n",
		"'" to "\\\'",
		"\r" to ""
	)
	 


	private fun imgToBase64(mdText: String): String {
        val matcher = IMAGE_PATTERN.matcher(mdText)
        if (!matcher.find()) {
            return mdText
        }

        val imgPath = matcher.group(2)
        imgPath?.let {
            if (isUrlPrefix(it) || !isPathExCheck(it)) {
                return mdText
            }
            val baseType = imgEx2BaseType(it)
            if (baseType.isEmpty()) return mdText
            val file = File(it)

            val bytes = ByteArray(file.length().toInt())
            FileInputStream(file).use { input ->
                input.buffered().read(bytes, 0, bytes.size)
            }

            val base64Img = baseType + Base64.encodeToString(bytes, Base64.NO_WRAP)
            return mdText.replace(it, base64Img)
        }

        return ""
    }

	private fun isUrlPrefix(text: String): Boolean {
		return text.startsWith("http://", "https://")
	}

	private fun isPathExCheck(text: String): Boolean {
		return text.endsWith(".png", ".jpg", ".jpeg", ".gif")
	}

	private fun imgEx2BaseType(text: String)  = when {
		text.endsWith(".png") -> "data:image/png;base64,"
		text.endsWith(".jpg") || text.endsWith(".jpeg") -> "data:image/jpg;base64,"
		text.endsWith(".gif") -> "data:image/gif;base64,"
		else -> ""
	}
	    

	
    
}