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
 * A layer of a map. A layer is a two-dimensional grid of cells which are all on the same y-level.
 * Each cell has a location that is unique within the layer. The layer is responsible for managing the cells and
 * providing access to them.
 */
interface MapLayer {

    /**
     * The layer number of this layer. The layer number is used to determine the order in which layers are drawn.
     * It is equal to the y dimension of the map it belongs to.
     */
    val layer: Int

    /**
     * Returns the cell at the specified [location] or throws an exception if there is no cell at the specified location.
     * The y coordinate of the location is ignored.
     */
    fun getCellAt(location: Location): Cell

    /**
     * Returns a set of all cells in this layer. There is no guarantee that the cells are ordered in any way.
     * If you need an ordered list/grid of cells, use [getCellsAsGrid].
     *
     */
    fun getCells(): Set<Cell>

    /**
     * Returns a list of all cells in this layer as a grid. The grid is a list of rows, where each row is a list of cells.
     * The cells are ordered by their x and y coordinates.
     *
     * For example, the first element of the returned list contains the first row of cells, the second element contains the
     * second row of cells, and so on. Each row (which is also a list) contains the cells of that row, ordered by their x value.
     * @return a grid of cells
     */
    fun getCellsAsGrid(): List<List<Cell>>
}