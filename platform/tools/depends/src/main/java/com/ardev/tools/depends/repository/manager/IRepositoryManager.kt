package com.ardev.tools.depends.repository.manager

import java.io.File

public interface IRepositoryManager {

var cacheDirectory: File

fun initialize()

fun getPom(declaration: String): PomModel?

fun getLibrary(pom: PomModel): File?

fun addRepository(name: String, url: String)

fun addRepository(repository: Repository)

}