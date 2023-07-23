package com.ardev.tools.depends.repository

import java.io.File
import java.io.IOException
import java.io.InputStream

class RemoteRepository(override val name: String, private var url: String) : Repository() {

    private val localRepository = LocalRepository(name)

    init {
        if (!url.endsWith("/")) url.plus("/")
        
    }    
    
    override var cacheDirectory: File = File("")
        get() = field
    	set(value) {
    	field = value
        localRepository.cacheDirectory = value
	}

    override fun getFile(path: String): File? {
         val file = localRepository.getFile(path)
        return if (file != null && file.exists()) {
            file
        } else {
            getFileFromNetwork(path)
        }     
    }
    
    override fun getCachedFile(path: String): File? {
        return localRepository.getCachedFile(path)
    }
    
     override fun getInputStream(path: String): InputStream? {
        val file = getFile(path)
        return if (file != null && file.exists()) {
            file.inputStream()
        } else null
    }
    
    private fun getFileFromNetwork(path: String): File? {       
        try {
        val inputStream = URL("$url$path").openStream()
            return if (inputStream != null) {
                mLocalRepository.save(path, inputStream)
            } else null
        } catch (e: Exception) {
            return null
        }
    }
    
}