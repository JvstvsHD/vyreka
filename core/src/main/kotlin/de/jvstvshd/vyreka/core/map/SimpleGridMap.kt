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
 * A simple implementation of a [VyrekaMap] that is used in a grid-based map.
 * @param width the width of the map
 * @param height the height of the map
 * @param depth the depth of the map
 * @see VyrekaMap
 * @since 1.0.0
 */
open class SimpleGridMap(override val width: Int, override val height: Int, override val depth: Int) : VyrekaMap {

    val cells: Array<Array<Array<Cell?>>> by lazy { Array(width) { Array(height) { arrayOfNulls(depth) } } }

    override fun getCellAtOrNull(location: Location): Cell? {
        return if (isValidLocation(location)) {
            cells[location.x][location.y][location.z]
        } else {
            null
        }
    }

    override fun setCell(cell: Cell) {
        val location = cell.location
        if (isValidLocation(location)) {
            cells[location.x][location.y][location.z] = cell
        } else {
            throw IllegalArgumentException("Invalid location: $location")
        }
    }

    override fun removeCellAt(location: Location): Cell? {
        if (isValidLocation(location)) {
            val cell = cells[location.x][location.y][location.z]
            cells[location.x][location.y][location.z] = null
            return cell
        } else {
            throw IllegalArgumentException("Invalid location: $location")
        }
    }

    private fun isValidLocation(location: Location): Boolean {
        return location.x in 0 until width && location.y in 0 until height && location.z in 0 until depth
    }

    override fun cells(): List<Cell> {
        return cells.flatten().flatMap { it.toList() }.filterNotNull()
    }
}