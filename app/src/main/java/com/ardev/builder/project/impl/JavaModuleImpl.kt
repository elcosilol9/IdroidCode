package com.ardev.builder.project.impl

import com.ardev.builder.model.Library
import com.ardev.builder.project.api.JavaModule
import com.ardev.builder.common.util.StringSearch
import java.io.File
import java.io.IOException
import java.lang.reflect.Method
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.Arrays

class JavaModuleImpl(root: File) : ModuleImpl(root), JavaModule {
    val mClassFiles: MutableMap<String, File> = hashMapOf()
    val mJavaFiles: MutableMap<String, File> = hashMapOf()
    val mLibraryHashMap: MutableMap<String, Library> = hashMapOf()
    val mInjectedClassesMap: MutableMap<String, File> = hashMapOf()
    val mLibraries: MutableSet<File> = hashSetOf()

    override val resourcesDir: File = File(rootFile, "src/main/resources")
     
    override val javaDir: File = File(rootFile, "src/main/java")
    
    override val libsDir: File = File(rootFile, "libs") 
    
    override fun addJavaFile(javaFile: File) {
        if (javaFile.extension != "java") return
        val className = StringSearch.packageName(javaFile)?.let { "$it.${javaFile.nameWithoutExtension}" } ?: javaFile.nameWithoutExtension
        mJavaFiles[className] = javaFile
    }

    override fun getJavaFile(packageName: String): File? = mJavaFiles[packageName]
        
    override fun removeJavaFile(packageName: String) {
        mJavaFiles.remove(packageName)
    }
    
    override fun getJavaFiles(): Map<String, File> = mJavaFiles.toMap()  
    
    override fun addLibrary(jar: File) {
      if (jar.extension != "jar") return
        runCatching {   
            JarFile(jar).use { jarFile ->
            putJar(jar)
            mLibraries.add(jar)
        }.onFailure {}        
    }

    override fun putLibraryHashes(hashes: Map<String, Library>) {
        mLibraryHashMap.putAll(hashes)
    }

    override fun getLibrary(hash: String): Library? = mLibraryHashMap[hash]
         
    override fun getLibraries(): List<File> = mLibraries.toList()
            
    override fun addInjectedClass(file: File) {
        mInjectedClassesMap[StringSearch.packageName(file)] = file
    }

    override fun getInjectedClasses(): Map<String, File> = mInjectedClassesMap.toMap()    
    
    override fun getAllClasses(): Set<String> {
        return (mJavaFiles.keys + mClassFiles.keys).toSet()
    }
    
    override fun open() {
        super.open()
    }

    override fun index() {
        super.index()
        runCaching {
            putJar(getBootstrapJarFile())
        }.onFailure {}
        
        if (javaDir.exists()) {
           javaDir.walkByExtension("java")
            .forEach { addJavaFile(it) }
          }

       File(buildDir, "libs").listFiles(File::isDirectory)?.forEach { dir ->
            File(dir, "classes.jar").let {
            if (it.exists()) addLibrary(it)
            }
        }
       
       libsDir.listFiles { it.isFile && it.extension == "jar"  }?.forEach { file -> addLibrary(file) }
    }

    override fun clear() {
        mJavaFiles.clear()
        mLibraries.clear()
        mLibraryHashMap.clear()
    }
    
    
    private fun putJar(file: File) {
        if (file == null) return
        JarFile(file).use { jar ->
            val entries: Enumeration<JarEntry> = jar.entries()
            while (entries.hasMoreElements()) {
                val entry: JarEntry = entries.nextElement()
                if (!entry.name.endsWith(".class")) continue
                if (entry.name.contains("$")) continue     
                val packageName: String = entry.name.replace("/", ".")
                    .substring(0, entry.name.length - ".class".length)
                mClassFiles[packageName] = file
            }
        }
    }
}
