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

package de.jvstvshd.vyreka.path

import de.jvstvshd.vyreka.core.Locatable
import de.jvstvshd.vyreka.core.Location
import de.jvstvshd.vyreka.core.cell.Cell
import de.jvstvshd.vyreka.core.map.VyrekaMap
import de.jvstvshd.vyreka.path.Path.Empty.end
import de.jvstvshd.vyreka.path.Path.Empty.start
import de.jvstvshd.vyreka.path.routing.RoutingAlgorithm
import de.jvstvshd.vyreka.path.routing.RoutingResult

/**
 * Finds a path from the start location to the end location using the specified algorithm and cost supplier.
 * @param start the start location.
 * @param end the end location.
 * @param algorithm the algorithm to use.
 * @param cost the cost supplier.
 * @see RoutingAlgorithm.findPath
 * @since 1.0.0
 */
fun VyrekaMap.findPath(
    start: Location,
    end: Location,
    algorithm: RoutingAlgorithm,
    cost: CellTravelCostSupplier
): RoutingResult {
    return algorithm.findPath(getCellAt(start), getCellAt(end), cost)
}

/**
 * Finds a path from the start cell (this) to the end cell using the specified algorithm and cost supplier.
 * @param end the end cell.
 * @param algorithm the algorithm to use.
 * @param cost the cost supplier.
 * @see findPath
 * @since 1.0.0
 */
fun Cell.findPathTo(
    end: Cell,
    algorithm: RoutingAlgorithm,
    cost: CellTravelCostSupplier
): RoutingResult {
    return algorithm.findPath(this, end, cost)
}

/**
 * Creates a new path that only contains the specified cell. The path has a length of 0. It starts and ends at the specified cell.
 * @return a zero-length path that only contains the specified cell.
 * @since 1.0.0
 */
fun Cell.path(): Path {
    return SimplePath(this, 0.0, listOf(this))
}

/**
 * Checks whether this location is adjacent to the other location. If [includeDiagonal] is true, the method will also
 * return true if the locations are diagonal to each other.
 * @param other the other location.
 * @param includeDiagonal whether to include diagonal locations.
 * @return true if the locations are adjacent to each other, false otherwise.
 * @since 1.0.0
 */
fun Location.isAdjacentTo(other: Location, includeDiagonal: Boolean = false): Boolean {
    if (includeDiagonal) {
        return distanceSquared(other) <= 2
    }
    return distanceSquared(other) <= 1
}

/**
 * Checks whether this locatable is adjacent to the other locatable. If [includeDiagonal] is true, the method will also
 * return true if the locatables are diagonal to each other.
 * @param other the other locatable.
 * @param includeDiagonal whether to include diagonal locatables.
 * @return true if the locatables are adjacent to each other, false otherwise.
 * @since 1.0.0
 */
fun Locatable.isAdjacentTo(other: Locatable, includeDiagonal: Boolean = false): Boolean {
    return location.isAdjacentTo(other.location, includeDiagonal)
}