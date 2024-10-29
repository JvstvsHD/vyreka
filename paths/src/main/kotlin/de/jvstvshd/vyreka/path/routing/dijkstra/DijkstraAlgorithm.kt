/*
 * This file is part of vyreka, a dynamic mapping tool designed to navigate grid-based locations and find optimal paths.
 *
 * MIT License
 *
 * Copyright (c) 2024 JvstvsHD
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.jvstvshd.vyreka.path.routing.dijkstra

import de.jvstvshd.vyreka.core.cell.Cell
import de.jvstvshd.vyreka.core.cell.CellAccessMode
import de.jvstvshd.vyreka.path.CellTravelCostSupplier
import de.jvstvshd.vyreka.path.Path
import de.jvstvshd.vyreka.path.routing.RoutingAlgorithm
import de.jvstvshd.vyreka.path.routing.RoutingResult
import de.jvstvshd.vyreka.path.path
import java.util.*
import kotlin.time.measureTimedValue

/**
 * Dijsktra's algorithm for finding the optimal path between two cells.
 * Short description of the algorithm:
 * 1. Create a priority queue of paths.
 * 2. Add the start cell to the queue.
 * 3. While the last cell in the path is not the end cell:
 *     1. For each neighbor of the last cell:
 *          1. Fork the path to the neighbor.
 *          2. Add the forked path to the queue.
 *     2. Poll the queue to get the next path.
 *     3. Repeat until the last cell in the path is the end cell.
 * 4. Return the path.
 * For more information, refer to [https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm](Dijkstra's algorithm on Wikipedia).
 */
object DijkstraAlgorithm : RoutingAlgorithm {

    override fun findPath(start: Cell, end: Cell, travelCost: CellTravelCostSupplier): RoutingResult {
        validateInput(start, end)
        val pathsQueue: Queue<Path> = PriorityQueue(Comparator.naturalOrder())
        var path = start.path()
        var actions: Long = 0
        val tv = measureTimedValue {
            while (path.getLast().location != end.location) {
                for (neighbor in path.getLast()
                    .getNeighbors(CellAccessMode.ACCESSIBLE)) {
                    val forkedPath = path.forkTo(neighbor, travelCost)
                    actions++
                    val newLast = forkedPath.getLast()
                    /*if (forkedPath.currentCost < newLast.currentFastestVisit) {
                        newLast.currentFastestPath = forkedPath
                        pathsQueue.add(forkedPath)
                    }*/
                }
                path = pathsQueue.poll()
            }
            path
        }
        return RoutingResult(tv.value, tv.duration, actions)
    }

    override fun getAlgorithmName(): String {
        return "dijkstra"
    }

    private fun validateInput(start: Cell, end: Cell) {
        require(start != end) { "Start and end cells must be different" }
        require(start.map == end.map) { "Start and end cells must be on the same map" }
    }
}