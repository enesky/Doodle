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
            rootProject.getDependencyNodes(dependencyTree)

            // Generate dot file for mermaid
            val dotFile = File("$rootDir/tools/dependency-graph/project.dot")
            dotFile.generateDotFile(dependencyTree)

            // Generate dot file for graphviz
            val dotDigraphFile = File("$rootDir/tools/dependency-graph/project_digraph.dot")
            dotDigraphFile.generateDigraphDotFile(dependencyTree, rootProject.name)

            println("------------------------------------------------------------------------------------")
            println("Dependency Graph Results:")

            // Generate png files with graphviz
            dotDigraphFile.generatePngWithGraphviz()

            // Show result
            val mermaidEditorUrl = "https://mermaid-js.github.io/mermaid-live-editor/"
            println("-> Copy result ${dotFile.absolutePath} to $mermaidEditorUrl show graph")
            val graphvizEditorUrl = "https://dreampuf.github.io/GraphvizOnline/"
            println("-> Copy result ${dotDigraphFile.absolutePath} to $graphvizEditorUrl show digraph")
            println("------------------------------------------------------------------------------------")
        }
    }
}

data class ElementNode(
    var moduleName: String = "",
    var dependencyNode: MutableList<ElementNode> = mutableListOf(),
)

private fun collectNode(treeMap: MutableMap<String, ElementNode>, moduleName: String): ElementNode {
    return treeMap.getOrPut(moduleName) {
        ElementNode().apply { this.moduleName = moduleName }
    }
}

private fun Project.getDependencyNodes(treeMap: MutableMap<String, ElementNode>) {
    val queue: MutableList<Project> = mutableListOf(this)
    while (queue.isNotEmpty()) {
        val firstItem = queue.removeAt(0)
        val currentNode = collectNode(treeMap, firstItem.name)
        queue.addAll(firstItem.childProjects.values)
        firstItem.configurations.all { config ->
            config.dependencies
                .withType(ProjectDependency::class.java)
                .map { it.dependencyProject }
                .forEach { dependency ->
                    if (firstItem.name != dependency.name) {
                        val childNode = collectNode(treeMap, dependency.name)
                        if (currentNode.dependencyNode.none { it.moduleName == childNode.moduleName }) {
                            currentNode.dependencyNode.add(childNode)
                        }
                    }
                }
            true
        }
    }
}

private fun File.generateDotFile(treeMap: MutableMap<String, ElementNode>) {
    parentFile.mkdirs()
    writeText("graph TD\n")
    treeMap.forEach { (_, element) ->
        element.dependencyNode.forEach { childElement ->
            appendText("  ${element.moduleName}-->${childElement.moduleName}\n")
        }
    }
}

private fun File.generateDigraphDotFile(treeMap: MutableMap<String, ElementNode>, projectName: String) {
    parentFile.mkdirs()
    writeText("digraph {\n")
    appendText("  graph [label=\"${projectName}\\n \",labelloc=t,fontsize=42,ranksep=1.4];\n")
    appendText("  node [style=filled, fillcolor=\"#F96D00\", fontsize=24];\n")
    appendText("  rankdir=TB;\n\n  # Dependencies\n\n")
    treeMap.forEach { (_, element) ->
        element.dependencyNode.forEach { childElement ->
            appendText("  \"${element.moduleName}\" -> \"${childElement.moduleName}\"\n")
        }
    }
    appendText("}\n")
}

@Suppress("TooGenericExceptionThrown", "SwallowedException")
private fun File.generatePngWithGraphviz() {
    try {
        val process = Runtime
            .getRuntime()
            .exec("dot -Tpng -O project_digraph.dot", null, parentFile)
        process.waitFor()
        if (process.exitValue() != 0) {
            throw RuntimeException(
                process.errorStream.bufferedReader().use { it.readText() },
            )
        }
        // Show result
        println("-> Check $absolutePath.png to see generated dependency graph")
    } catch (e: Exception) {
        // Show error
        println("-> Graphviz tool is not installed on your machine.")
        println("-> Please install it from https://graphviz.org/download/")
    }
}
