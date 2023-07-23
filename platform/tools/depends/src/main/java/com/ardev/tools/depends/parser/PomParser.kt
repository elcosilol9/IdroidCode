package com.ardev.tools.depends.parser

import java.io.File
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.util.ArrayList
import java.util.HashMap
import java.util.Map

class PomParser(private val repository: RepositoryManager) {
    
    private var parent: Pom? = null
    private val properties: MutableMap<String, String> = HashMap()
    
    companion object {
        private val VARIABLE_PATTERN = "\\$\\{(.*?)\\}".toRegex()
    }

    fun parse(file: File): PomModel? {
        val element = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
            InputSource(StringReader(file.readText()))
        ).documentElement
        return parseProject(element).takeIf { element.tagName == "project" }
    }

    private fun parseProject(projectElement: Element): PomModel {
        val propertyElement = projectElement.getElementsByTagName("properties")
        if (propertyElement.length > 0) {
            properties.putAll(parseProperties(propertyElement.item(0) as Element))
        }
        val pom = PomModel()
        for (child in projectElement.childNodes) {
            when (child.nodeName) {
                "dependencies" -> pom.dependencies = parseDependencies(child as Element)
                "dependencyManagement" -> pom.managedDependencies = parseDependencies(child as Element)
                "parent" -> {
                    parent = parseParent(child as Element)
                    pom.parent = parent
                }
                "groupId" -> pom.groupId = child.textContent
                "artifactId" -> pom.artifactId = child.textContent
                "version" -> pom.version = child.textContent
                "packaging" -> pom.packaging = child.textContent
                "name" -> pom.name = child.textContent
                "properties" -> {               
                }
            }
        }
        return pom
    }

    private fun parseParent(parentElement: Element): PomModel {
       val dependency = DependencyModel()
        for (child in parentElement.childNodes) {
            when (child.nodeName) {
                "groupId" -> {
                if (child.length < 1) return PomModel()
                dependency.groupId = child.textContent
                }
                "artifactId" -> {
                if (child.length < 1) return PomModel()
                dependency.artifactId = child.textContent
                }
                "version" -> {
                if (child.length < 1) return PomModel()
                var version = child.textContent ?: ""
                val name = VARIABLE_PATTERN.find(version)?.groupValues?.getOrNull(1)
                val property = name?.let { properties[it] ?: parent?.properties[it] }
                version = property ?: version
                dependency.versionName = version           
                }
                
            }
        }
        return repository.getPom(dependency.toString())
    }

    private fun parseDependencies(dependenciesElement: Element): List<DependencyModel> {
        val dependencies = ArrayList<DependencyModel>()
        for (dependencyElement in dependenciesElement.childNodes) {
            if (dependencyElement.nodeType == Node.ELEMENT_NODE && dependencyElement.nodeName == "dependency") {
                val dependency = DependencyModel()
                for (child in dependencyElement.childNodes) {
                    when (child.nodeName) {
                        "groupId" -> dependency.groupId = child.textContent
                        "artifactId" -> dependency.artifactId = child.textContent
                        "version" -> {
                        
                        if (child.length < 1) {
                val current = parent
                var found = false
                while (current != null) {
                    val managedDependencies = current.managedDependencies
                    for (managedDependency in managedDependencies) {
                        if (managedDependency.groupId != dependency.groupId) continue                      
                        if (managedDependency.artifactId != dependency.artifactId) continue                      
                        dependency.versionName = managedDependency.versionName
                        dependency.scope = managedDependency.scope
                        found = true
                        break
                    }
                    if (found) break
                    parent = current.parent
                }
                if (!found) continue
                  
                      } else {                 
                        dependency.versionName = normalizeText(child.textContent)                                
                         }
                      }
                        "type" -> dependency.type = child.textContent
                        "scope" -> dependency.scope = normalizeText(child.textContent)  
                        "optional" -> dependency.optional = child.textContent.toBoolean()
                        "exclusions" -> dependency.exclusions = parseExclusions(child as Element)
                    }
                }
                dependencies.add(dependency)
            }
        }
        return dependencies
    }

   private fun parseExclusions(exclusionsElement: Element): List<DependencyModel> {
      val exclusionDependency = ArrayList<DependencyModel>()
        for (exclusionElement in exclusionsElement.childNodes) {
            if (exclusionElement.nodeType == Node.ELEMENT_NODE && exclusionElement.nodeName == "exclusion") {
                val dependency = DependencyModel()
                for (child in exclusionElement.childNodes) {
                    when (child.nodeName) {
                        "groupId" -> dependency.groupId = normalizeText(child.textContent)
                        "artifactId" -> dependency.artifactId = normalizeText(child.textContent)
                    }
                }
                exclusionDependency.add(dependency)
            }
        }
        return exclusionDependency
    }

    private fun parseProperties(propertiesElement: Element): Map<String, String> {
        val properties = Map<String, String>()
        for (propertyElement in propertiesElement.childNodes) {
            if (propertyElement.nodeType == Node.ELEMENT_NODE) {
                val name = propertyElement.nodeName
                val value = normalizeText(propertyElement.textContent)
                properties[name] = value
            }
        }
        return properties
    }

    private fun normalizeText(str: String): String {
    var result = str
    VARIABLE_PATTERN.findAll(str).forEach { matchResult ->
        val key = matchResult.groupValues[1]
        val value = properties[key] ?: throw tException("Unknown property $key")
        result = result.replace("\${$key}", value)
    }
    return result
}
    
}