
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class ProjectsRepository {

    suspend fun loadProjects(await: Boolean): Flow<Resource<List<Project>>> {
        return flow {
          if (await) {
            emit(Resource.Loading)
            delay(1000)
            }
            emit(fetchProjects())
        }.flowOn(Dispatchers.Default)
    }

    suspend fun openProject(project: Project, await: Boolean): Flow<Resource<Project>> {
         return flow {
            if (await)  delay(1000)
            if (project.rootFile.isAppModule) {
            emit(Resource.Success(project))
            } else {
            emit(Resource.Error("Error opening project"))
            }
         }.flowOn(Dispatchers.Default)
    }
    
    private suspend fun fetchProjects(): Resource<List<Project>> {
        return runCatching {
            val projects = mutableListOf<Project>()
            val directories = File(Paths.projectsDir).listFiles { file ->
                file.isDirectory && file.isAppModule
            }?.sortedBy { it.lastModified() }
            if (directories != null && directories.isNotEmpty()) {
                directories.forEach { directory ->
                    projects.add(Project(File(directory.path)))
                }
                Resource.Success(projects)
            } else {
                Resource.Error("No projects found")
            }
        }.getOrElse {
            Resource.Error("Error loading projects: ${it.message}")
        }
    }
}
