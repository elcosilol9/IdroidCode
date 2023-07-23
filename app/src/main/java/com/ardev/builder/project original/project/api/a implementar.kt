fun getModuleForFile(file: File): Module? {
    return mModules.values.firstOrNull { isFileInModule(file, it) }
}

private fun isFileInModule(file: File, module: Module): Boolean {
    return runCatching {
        file.path.startsWith(module.rootFile.path)
    }.getOrElse { false }
}





class MyClass {
    private val customScope = CustomScope()

    fun doSomething() {
        customScope.launch {
            // Realizar operaciones asincrónicas aquí
        }
    }

    fun cleanup() {
        customScope.cancel()
    }
}

class CustomScope : CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO // Puedes utilizar el dispatcher que necesites

    fun cancel() {
        job.cancel()
    }
}

