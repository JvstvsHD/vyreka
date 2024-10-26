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

import de.jvstvshd.vyreka.cell.Cell

class SimplePath(override val start: Cell, override val currentCost: Int, currPath: List<Cell>) : Path {

    override var end: Cell? = null
        private set

    override var finished: Boolean = false
    private val currentPath = currPath

    override fun getCells(): List<Cell> = currentPath.toList()

    override fun getLength(): Int = currentPath.size

    override fun getLast(): Cell = currentPath.last()

    override fun forkTo(cell: Cell, cost: CellTravelCostSupplier, at: Int): Path {
        if (at >= 0) {
            val forkAt = currentPath[at]
            val subPath = subPathUntil(at)
            return SimplePath(
                start,
                currentCost + cost.getCost(forkAt, cell),
                subPath.getCells().toMutableList().apply { add(cell) })
        } else {
            return SimplePath(
                start,
                currentCost + cost.getCost(cell, getLast()),
                currentPath.toMutableList().apply { add(cell) })
        }
    }

    override fun subPath(from: Int, to: Int): Path {
        if (from < 0)
            throw IllegalArgumentException("from cannot be negative")
        if (from > to)
            throw IllegalArgumentException("from cannot be greater than to")
        if (from > currentPath.size)
            throw IllegalArgumentException("from cannot be greater than the length of this path")
        if (to > currentPath.size)
            throw IllegalArgumentException("to cannot be greater than the length of this path")

        val newPath = currentPath.subList(from, to + 1).toMutableList()
        return SimplePath(start, currentCost, newPath)
    }
}