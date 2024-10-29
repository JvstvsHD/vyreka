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

/**
 * A supplier that provides the cost of traveling between two cells in a juxtaposition. This will not calculate the cost
 * of a total path, but only the cost of traveling between two cells. The cost is represented as an integer, where a higher
 * value means a higher cost. The cost should be positive and non-negative.
 * @since 1.0.0
 */
interface CellTravelCostSupplier {

    /**
     * Calculates the cost of traveling between two locations. The cost should be positive and non-negative.
     */
    operator fun invoke(start: Location, end: Location): Int

    /**
     * Calculates the cost of traveling between two locatable objects. The cost should be positive and non-negative.
     * Note: If at least on of the locatable objects is a [de.jvstvshd.vyreka.core.Movable], only the current location
     * will be considered.
     */
    operator fun invoke(start: Locatable, end: Locatable): Int = invoke(start.location, end.location)

    /**
     * The maximum cost of traveling between two cells in a juxtaposition.
     */
    val maximumCost: Int

    /**
     * The minimum cost of traveling between two cells in a juxtaposition.
     */
    val minimumCost: Int
}