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

import de.jvstvshd.vyreka.Location
import de.jvstvshd.vyreka.cell.Cell
import de.jvstvshd.vyreka.map.LocationMap
import de.jvstvshd.vyreka.path.finder.PathFindingAlgorithm
import de.jvstvshd.vyreka.path.finder.PathFindingResult

/**
 * Finds a path from the start location to the end location using the specified algorithm and cost supplier.
 * @param start the start location.
 * @param end the end location.
 * @param algorithm the algorithm to use.
 * @param cost the cost supplier.
 */
fun LocationMap.findPath(
    start: Location,
    end: Location,
    algorithm: PathFindingAlgorithm,
    cost: CellTravelCostSupplier
): PathFindingResult {
    return algorithm.findPath(getCellAt(start), getCellAt(end), cost)
}

/**
 * Creates a new path that only contains the specified cell. The path has a length of 0. It starts and ends at the specified cell.
 * @return a zero-length path that only contains the specified cell.
 */
fun Cell.path(): Path {
    return SimplePath(this, 0, listOf(this))
}