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

package de.jvstvshd.vyreka.path.routing

import de.jvstvshd.vyreka.core.Locatable
import de.jvstvshd.vyreka.core.cell.Cell
import de.jvstvshd.vyreka.path.CellTravelCostSupplier

/**
 * A routing algorithm that finds the optimal path between two cells. The algorithm is responsible for finding the path
 * and returning the result.
 */
interface RoutingAlgorithm {

    /**
     * Finds the optimal path between the start and end cell using the given travel cost supplier.
     * @param start the start cell.
     * @param end the end cell.
     * @param travelCost the travel cost supplier.
     * @return the routing result.
     */
    fun findPath(start: Cell, end: Cell, travelCost: CellTravelCostSupplier): RoutingResult

    /**
     * Gets the name of the algorithm.
     */
    fun getAlgorithmName(): String
}