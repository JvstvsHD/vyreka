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

package de.jvstvshd.vyreka.cell

import de.jvstvshd.vyreka.Locatable
import de.jvstvshd.vyreka.map.LocationMap
import de.jvstvshd.vyreka.map.MapLayer
import java.nio.file.Path

interface Cell : Locatable {

    val layer: MapLayer
        get() = map.getLayer(location.y)

    val map: LocationMap

    /**
     * Computes all neighboring cells. That are cells for which only one dimension differs by exactly 1. Excluded
     * are diagonal cells that will be included in the returned list if [includeDiagonals] is true. If this cell is located
     * on the edge of the map, the returned list will not contain cells that are outside of the map (for example if this
     * cell has the location [0, 0] (y coordinate is omitted), the returned list will not contain the cell at [0, -1]).
     *
     * Depending on [what accessibility mode][mode] was chosen, this method may return all cells that are adjacent to this
     * or only those ones that are accessible from this cell, i.e. where no wall is between them two.
     * @param mode the accessibility mode to use
     * @param includeDiagonals whether to include diagonal cells
     * @return a list of all neighboring cells
     * @throws IllegalArgumentException if [mode] is [CellAccessMode.ACCESSIBLE] and this cell is a wall cell
     */
    fun getNeighbors(mode: CellAccessMode = CellAccessMode.ACCESSIBLE, includeDiagonals: Boolean = false): List<Cell>

    /**
     * Whether this cell can be accessed from the given location. This is used to determine whether a path can be
     * constructed from the given location to this cell. This implies that if the [other] cell is not accessible at all (e.g.
     * [a wall cell][cell.CompetitionCell.WallCell]), this method may return true as this cell is not fully seperated
     * by walls or is an entire wall itself. This behavior should not occur as a full wall cell cannot be the start/end of
     * a path so other may never be a wall cell.
     *
     * Depending on the underlying implementation, if the [other] cell is more than one cell away from this cell, this method
     * probably returns false.
     * @return true if this cell can be accessed from the given location, false otherwise
     * @throws IllegalArgumentException if the [other] cell is not on the same map as this cell
     */
    fun canBeAccessedFrom(other: Cell): Boolean
}