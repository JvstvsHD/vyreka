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

package de.jvstvshd.vyreka.path.finder.dijkstra

import de.jvstvshd.vyreka.cell.Cell
import de.jvstvshd.vyreka.cell.CellAccessMode
import de.jvstvshd.vyreka.path.CellTravelCostSupplier
import de.jvstvshd.vyreka.path.Path
import de.jvstvshd.vyreka.path.finder.PathFindingAlgorithm
import de.jvstvshd.vyreka.path.finder.PathFindingResult
import de.jvstvshd.vyreka.path.path
import java.util.*
import kotlin.time.measureTimedValue

object DijkstraAlgorithm : PathFindingAlgorithm {

    override fun findPath(start: Cell, end: Cell, travelCost: CellTravelCostSupplier): PathFindingResult {
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
        start.map.clearPaths()
        return PathFindingResult(tv.value, tv.duration, actions)
    }

    private fun validateInput(start: Cell, end: Cell) {
        require(start != end) { "Start and end cells must be different" }
        require(start.map == end.map) { "Start and end cells must be on the same map" }
    }
}

/*internal class DijkstraDelegateMap(val delegate: Map) : Map by delegate {

    val cellCache = mutableMapOf<Location, Cell>()

    override fun getCellAt(location: Location): Cell {
        return delegate.getCellAt(location).delegate()
    }

    override fun getStart(): Cell {
        return delegate.getStart().delegate()
    }

    override fun getEnd(): Cell {
        return delegate.getEnd().delegate()
    }

    @JvmName("delegateExt")
    private fun Cell.delegate(): Cell {
        return cellCache.getOrPut(location) { DijkstraDelegateCell(this, this@DijkstraDelegateMap) }
    }

    fun delegate(cell: Cell): Cell {
        return cell.delegate()
    }
}*/

/*
internal class DijkstraDelegateCell(val delegate: Cell, override val map: DijkstraDelegateMap) : Cell by delegate {

    var currentFastestVisit: Int = Int.MAX_VALUE


    override fun getNeighbors(mode: CellAccessMode, includeDiagonals: Boolean): List<Cell> {
        return delegate.getNeighbors(mode, includeDiagonals).map { (map as DijkstraDelegateMap).delegate(it) }
    }

    override fun path(): Path {
        return SimplePath(this, 0, listOf(this))
    }

    override fun toString(): String {
        return "DijkstraDelegateCell(delegate=$delegate, currentFastestVisit=$currentFastestVisit, currentFastestPath=$currentFastestPath)"
    }
}*/
