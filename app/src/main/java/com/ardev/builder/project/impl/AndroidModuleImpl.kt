package com.ardev.builder.project.impl

import com.ardev.builder.project.api.AndroidModule
import com.ardev.builder.common.util.StringSearch
import java.io.File
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Set
import java.util.function.Consumer

class AndroidModuleImpl(root: File) : JavaModuleImpl(root), AndroidModule {
    
    private val mKotlinFiles: MutableMap<String, File> = hashMapOf()
    private val mRClasses: MutableMap<String, File> = hashMapOf()
     
    override val kotlinDir: File = File(rootFile, "src/main/kotlin")
     
    override val resDir: File = File(rootFile, "src/main/res")
      
    override val assetsDir: File = File(rootFile, "src/main/assets")
      
    override val jniLibs: File = File(rootFile, "src/main/jniLibs")        
      
    override val manifestFile: File = File(rootFile, "src/main/AndroidManifest.xml")
        
    override val packageName: String? = null
                    
    override val targetSdk: Int = 33
      
    override val minSdk: Int = 21
           
   override fun addKotlinFile(ktFile: File) {
      val className = StringSearch.packageName(ktFile)?.let {
       "$it.${ktFile.nameWithoutExtension}" ?: ktFile.nameWithoutExtension
      }
       mKotlinFiles[className] = ktFile
    }

   override fun getKotlinFile(packageName: String): File? {
        return mKotlinFiles[packageName]
    }
   
    override fun getKotlinFiles(): Map<String, File> {
        return mKotlinFiles.toMap()
    }
    
     override fun addRClass(file: File) {
        mRClasses[StringSearch.packageName(file)] = file
    }

    override fun getRClasses(): Map<String, File> {
        return mRClasses.toMap()
    }

   override fun getAllClasses(): Set<String> {
      return super.getAllClasses() + mKotlinFiles.keys
    }
        
   override fun open() {
        super.open()
    }

   override fun index() {
        super.index()
     val kotlinConsumer: Consumer<File> = Consumer { addKotlinFile(it) }
       if (javaDir.exists()) {
            javaDir.walkByExtension("kt")
            .forEach { kotlinConsumer.accept(it) }
        }

       if (kotlinDir.exists()) {
            kotlinDir.walkByExtension("kt")
            .forEach { kotlinConsumer.accept(it) }
        }
    }

   override fun clear() {
        super.clear()
        
    }
}
