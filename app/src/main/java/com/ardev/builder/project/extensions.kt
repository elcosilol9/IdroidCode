fun createMainModule(rootFile: File): Module = when {
      is rootFile.isApplication -> ApplicationModuleImpl(File(rootFile, "app")
      is rootFile.isAndroidLibrary -> AndroidLibraryModuleImpl(File(rootFile)
      is rootFile.isLibrary -> LibraryModuleImpl(File(rootFile)
      else -> ModuleImpl(File(rootFile)
    }
