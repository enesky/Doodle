package dev.enesky.build_logic.convention.plugins.common

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import java.io.File

/**
 * Needs graphviz installed on local machine -> https://graphviz.org/download/
 *
 * Run with -> ./gradlew dependencyGraph --no-configuration-cache
 *
 * Inspired by -> <a href="https://github.com/mikeFei1000/dependency-graph-plugin-task">dependency-graph-plugin-task</a>
 **/
class DependencyGraphPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        tasks.register("dependencyGraph") {

            // Get module dependencies tree
            val dependencyTree = linkedMapOf<String, ElementNode>()
            val queue: MutableList<Project> = mutableListOf(rootProject)

            while (queue.isNotEmpty()) {
                val firstItem = queue.removeAt(0)
                val currentNode = collectNode(dependencyTree, firstItem.name)
                queue.addAll(firstItem.childProjects.values)
                firstItem.configurations.all { config ->
                    config.dependencies
                        .withType(ProjectDependency::class.java)
                        .map { it.dependencyProject }
                        .forEach { dependency ->
                            if (firstItem.name != dependency.name) {
                                val childNode = collectNode(dependencyTree, dependency.name)
                                if (currentNode.dependencyNode.none { it.moduleName == childNode.moduleName }) {
                                    currentNode.dependencyNode.add(childNode)
                                }
                            }
                        }
                    true
                }
            }

            // Generate dot file for mermaid
            val dotFile = File("$rootDir/config/dependency-graph/project.dot")
            dotFile.parentFile.mkdirs()
            dotFile.writeText("graph TD\n")
            dependencyTree.forEach { (_, element) ->
                element.dependencyNode.forEach { childElement ->
                    dotFile.appendText("  ${element.moduleName}-->${childElement.moduleName}\n")
                }
            }

            // Generate dot file for graphviz
            val dotDigraphFile = File("$rootDir/config/dependency-graph/project_digraph.dot")
            dotDigraphFile.parentFile.mkdirs()
            dotDigraphFile.writeText("digraph {\n")
            dotDigraphFile.appendText("  graph [label=\"${rootProject.name}\\n \",labelloc=t,fontsize=42,ranksep=1.4];\n")
            dotDigraphFile.appendText("  node [style=filled, fillcolor=\"#F96D00\", fontsize=24];\n")
            dotDigraphFile.appendText("  rankdir=TB;\n\n  # Dependencies\n\n")
            dependencyTree.forEach { (_, element) ->
                element.dependencyNode.forEach { childElement ->
                    dotDigraphFile.appendText("  \"${element.moduleName}\" -> \"${childElement.moduleName}\"\n")
                }
            }
            dotDigraphFile.appendText("}\n")

            println("------------------------------------------------------------------------------------")
            println("Dependency Graph Results:")

            // Generate png files with graphviz
            try {
                val process = Runtime.getRuntime()
                    .exec("dot -Tpng -O project_digraph.dot", null, dotDigraphFile.parentFile)
                process.waitFor()
                if (process.exitValue() != 0) {
                    throw RuntimeException(
                        process.errorStream.bufferedReader().use { it.readText() }
                    )
                }
                // Show result
                println("-> Check ${dotDigraphFile.absolutePath}.png to see generated dependency graph")
            } catch (e: Exception) {
                // Show error
                println("-> Graphviz tool is not installed on your machine.")
                println("-> Please install it from https://graphviz.org/download/")
            }

            // Show result
            println("-> Copy result ${dotFile.absolutePath} to https://mermaid-js.github.io/mermaid-live-editor/ show graph")
            println("-> Copy result ${dotDigraphFile.absolutePath} to https://dreampuf.github.io/GraphvizOnline/ show digraph")
            println("------------------------------------------------------------------------------------")
        }
    }
}

data class ElementNode(
    var moduleName: String = "",
    var dependencyNode: MutableList<ElementNode> = mutableListOf()
)

private fun collectNode(treeMap: MutableMap<String, ElementNode>, moduleName: String): ElementNode {
    return treeMap.getOrPut(moduleName) { ElementNode().apply { this.moduleName = moduleName } }
}
