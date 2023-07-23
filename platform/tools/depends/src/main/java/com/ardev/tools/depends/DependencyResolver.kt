package com.ardev.tools.depends

class DependencyResolver(private val repository: RepositoryManager) {

    private val resolvedPoms: MutableMap<PomModel, String> = HashMap()
    private lateinit var  listener: ResolveListener

    fun setResolveListener(listener: ResolveListener) {
        this.listener = listener
    }

    fun resolveDependencies(declaredDependencies: List<DependencyModel>): List<PomModel> {
        val poms: MutableList<PomModel> = ArrayList()
        declaredDependencies.forEach { dependency ->
            listener.onResolve("Getting POM: $dependency")
            repository.getPom(dependency.toString())?.let { pom ->
                pom.excludes = dependency.excludes
                pom.isUserDefined = true
                poms.add(pom)
            } ?: listener.onFailure("Unable to retrieve POM of $dependency")
        }
        return resolve(poms)
    }

    fun resolve(declaredDependencies: List<PomModel>): List<PomModel> {
        declaredDependencies.forEach { pom ->
            resolve(pom)
        }
        return ArrayList(resolvedPoms.keys)
    }

    private fun resolve(pom: PomModel) {
        if (resolvedPoms.containsKey(pom)) {
            if (pom.isUserDefined) {
                resolvedPoms.remove(pom)
            } else {
                resolvedPoms[pom]?.let { resolvedVersion ->
                    val thisVersion = pom.versionName
                    val result = getHigherVersion(resolvedVersion, thisVersion)
                    if (result == 0) return             
                    if (result > 0) {
                        return 
                       }  else {
                        resolvedPoms.remove(pom)
                    }
                }
            }
        }
        listener.onResolve("Resolving $pom")
        val excludes = pom.excludes
        pom.dependencies.filter { it.scope != "test" }.forEach { dependency ->
            val excluded = excludes.any { ex ->
                ex?.let {
                    it.groupId == dependency.groupId &&
                    it.artifactId == dependency.artifactId &&
                    (it.versionName.isNullOrEmpty() || it.versionName == dependency.versionName)
                } ?: false
            }
            if (excluded) {
                return@forEach
            }
            repository.getPom(dependency.toString())?.let { resolvedPom ->
                if (!resolvedPom.equals(pom)) {
                    resolvedPom.excludes = excludes
                    resolve(resolvedPom)
                }
            } ?: listener.onFailure("Failed to resolve $dependency")
        }
        resolvedPoms[pom] = pom.versionName
    }

    private fun getHigherVersion(firstVersion: String?, secondVersion: String?): Int {
        return ComparableVersion(firstVersion).compareTo(ComparableVersion(secondVersion))
    }
}