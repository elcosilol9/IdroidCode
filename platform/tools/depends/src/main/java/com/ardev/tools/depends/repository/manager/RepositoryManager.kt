package com.ardev.tools.depends.repository.manager

import java.io.File

class RepositoryManager : IRepositoryManager {
   
    private val repositories = mutableListOf<Repository>()
    private val pomList = mutableListOf<PomModel>()

   override var cacheDirectory: File = File("")
        get() = field
        set(value) {
            field = value
        }
        
   override fun initialize() {
        if(cacheDirectory.isMissing) {
        throw Exception("Cache directory is not set.")
        }
     repositories.forEach { repository ->
       repository.cacheDirectory = cacheDirectory
    val rootDir = repository.rootDirectory
    if (rootDir.isMissing) return@forEach
    rootDir.walkByExtension("pom")
        .forEach { pom ->
        try {
            pomList.add(PomParser().parse(pom))
        } catch (e: XmlPullParserException) {}
          catch (e: IOException) {}
          
      }
    }
  }

    override fun getPom(declaration: String): PomModel? {
    val pomNames = declaration.parsePomDeclaration 
    if (pomNames.isEmpty()) return null
    pomList.forEach { pom ->
        when {
            pomNames[0] != pom.groupId -> return@forEach
            pomNames[1] != pom.artifactId -> return@forEach
            pomNames[2] != pom.versionName -> return@forEach
            else -> return pom
        }
    }
    return getPomFromUrls(pomNames)
     }

    override fun getLibrary(pom: PomModel): File? {
    repositories.forEach { repository ->
        val file = repository.getCachedFile(pom.libraryPath)
        if (file != null && file.exists()) return file
    }
    
    repositories.forEach { repository ->
        val file = repository.getFile(pom.libraryPath)
        if (file != null && file.exists()) return file
    }
    return null
    }
       
        
    override fun addRepository(name: String, url: String) {
       addRepository(RemoteRepository(name, url))
    }

    override fun addRepository(repository: Repository) {
      repositories.add(repository)
    }
    
    

    private fun getPomFromUrls(names: Array<String>): PomModel? {
    val inputStream = getFromUrls("${names.pathFromDeclaration}.pom")
    if (inputStream != null) {
        try {
            return PomParser(this).parse(inputStream.readText()).apply {
            groupId = names[0]
            artifactId = names[1]
            versionName = names[2]
             }.also {
            pomList.add(it)
            }
        } catch (e: IOException) {}  
           
    }
    return null
    }

    
    private fun getFromUrls(appendUrl: String): InputStream? {
     repositories.forEach { 
        try {
            val inputStream = it.getInputStream(appendUrl)
            if (inputStream != null)  return inputStream
        } catch (e: IOException) { }
        
    }
    return null
    }

}