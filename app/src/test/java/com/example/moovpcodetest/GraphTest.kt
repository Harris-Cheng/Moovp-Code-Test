package com.example.moovpcodetest

import org.junit.Test
import java.util.LinkedList
import java.util.Queue


// Define a Graph class to represent a graph using an node list.
class Graph {
    companion object {
        // describe all paths for this graph
        val mainGraph = Graph().apply {
            addPath('A', 'B')
            addPath('A', 'D')
            addPath('A', 'H')
            addPath('B', 'C')
            addPath('B', 'D')
            addPath('C', 'D')
            addPath('C', 'F')
            addPath('D', 'E')
            addPath('E', 'F')
            addPath('E', 'H')
            addPath('F', 'G')
            addPath('G', 'H')
        }
    }

    private val nodesList = mutableMapOf<Char, MutableList<Char>>()

    // Function to add an path between 2 nodes
    fun addPath(a: Char, b: Char) {
        nodesList.computeIfAbsent(a) {
            mutableListOf()
        }.add(b)
        nodesList.computeIfAbsent(b) {
            mutableListOf()
        }.add(a)
    }

    // Function to check if node is not present in path
    private fun isNotVisited(node: Char, path: List<Char>): Boolean {
        return path.all { it != node }
    }

    // Function to find all paths between 2 nodes
    fun allPath(startingNode: Char, endingNode: Char): MutableList<List<Char>> {
        // List to store all possible path
        val allPaths = mutableListOf<List<Char>>()

        // Queue to store paths
        val queue: Queue<List<Char>> = LinkedList()

        // Enqueue the starting path
        queue.offer(mutableListOf(startingNode))

        // BFS traversal loop.
        while (queue.isNotEmpty()) {
            // Dequeue a path from the queue.
            val path = queue.poll() ?: continue

            // if last node is the desired node, store the path
            if (path.last() == endingNode) {
                allPaths.add(path)
            }

            // Traverse to all the nodes connected to
            // current node and push new path to queue
            val lastNode = nodesList[path.last()] ?: continue
            lastNode.forEach { node ->
                if (isNotVisited(node, path)) {
                    val newPath = mutableListOf<Char>().apply {
                        addAll(path)
                        add(node)
                    }
                    queue.offer(newPath)
                }
            }
        }

        return allPaths
    }
}

class GraphTest {
    @Test
    fun allPossiblePathBetweenAAndH() {
        val allPaths = Graph.mainGraph.allPath('A', 'H')
        allPaths.forEach { path ->
            print("${path}\n")
        }
        assert(true)
    }

    @Test
    fun shortestPath() {
        val allPaths = Graph.mainGraph.allPath('A', 'H')
        allPaths.minBy { it.size }.also {
            print(it)
        }
        assert(true)
    }
}