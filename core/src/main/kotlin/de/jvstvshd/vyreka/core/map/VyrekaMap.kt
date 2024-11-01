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

package de.jvstvshd.vyreka.core.map

import de.jvstvshd.vyreka.core.Location
import de.jvstvshd.vyreka.core.cell.Cell

/**
 * A map of locations that are arranged in a 3-dimensional grid. Each location within bounds should contain a cell, except
 * stated otherwise by an implementation. A cell can be accessed through its location. The map has a width, height and depth,
 * which represent the x, y and z axes respectively.
 * @since 1.0.0
 * @see Cell
 * @see Location
 * @see LayeredMap
 */
interface VyrekaMap {

    /**
     * The width of the map, represented as the x-axis.
     */
    val width: Int

    /**
     * The height of the map, represented as the y-axis.
     */
    val height: Int

    /**
     * The depth of the map, represented as the z-axis.
     */
    val depth: Int

    /**
     * Tries to retrieve a cell at the given location. If there is no cell at the location, for example since the location
     * is out of bounds, null is returned. However, as long as the location is within the bounds of the map, this method
     * should never return null.
     * @param location the location to retrieve the cell from
     * @return the cell at the location, or null if there is no cell at the location
     */
    fun getCellAtOrNull(location: Location): Cell?

    /**
     * Tries to retrieve a cell at the given location. If there is no cell at the location, for example since the location
     * is out of bounds, an exception is thrown. This method should only be used if it is guaranteed that there is a cell
     * at the location.
     * @param location the location to retrieve the cell from
     * @return the cell at the location
     * @throws IllegalArgumentException if there is no cell at the location
     */
    fun getCellAt(location: Location): Cell {
        return getCellAtOrNull(location) ?: throw IllegalArgumentException("No cell at $location")
    }

    /**
     * Removes the cell at the given location. If there is no cell at the location, null is returned. Otherwise, the removed
     * cell is returned.
     * @param location the location to remove the cell from
     * @return the removed cell, or null if there is no cell at the location
     */
    fun removeCellAt(location: Location): Cell?

    /**
     * Sets the cell at the given location to the given cell. If there is already a cell at the location, it will be
     * overwritten by the new cell.
     * @param location the location to set the cell at
     * @param cell the cell to set at the location
     * @throws IllegalArgumentException if the location is out of bounds
     */
    fun setCell(cell: Cell)

    /**
     * Creates a list of all cells in the map. The order of the cells is not specified and may vary between different
     * implementations.
     */
    fun cells(): List<Cell>
}