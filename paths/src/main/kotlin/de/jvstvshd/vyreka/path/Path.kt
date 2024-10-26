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

/**
 * A path is a list of cells that are connected to each other and walkable entirely.
 * An object always consists of at least one cell.
 */
interface Path : Comparable<Path> {

    /**
     * The start cell of the path.
     */
    val start: Cell

    /**
     * The end cell of the path. This may be null if this path only consists of [one cell][start] or does not yet have
     * an set end. For example, if this path gets discarded as it is not the most cost-efficient path, there never will
     * be and end cell.
     */
    val end: Cell?

    /**
     * The current cost of this path.
     */
    val currentCost: Int

    /**
     * Whether this path is finished or not. A path is finished if the [end] cell is reached.
     */
    var finished: Boolean

    /**
     * A list of all cells currently enlisted under this path. This list will always be ordered so that
     * index 0 will be equal to [start] and all following cells will be connected to its predecessor. The last element
     * will, if [there are more than 1 cells][getLength], be equal to [end]. Modifying this list will have no effect.
     * @return a list of all cells enlisted under this path
     * @see getLength
     */
    fun getCells(): List<Cell>

    /**
     * Returns the length of this path, which is the amount of cells enlisted under this path. This will yield the exact
     * same result as `getCells().size`.
     * @return the length of this path, which is the amount of cells enlisted under this path
     */
    fun getLength(): Int

    /**
     * @return the current last cell of this path; equivalent to `getCells().last()`
     */
    fun getLast(): Cell

    /**
     * Forks the path at the [given position][at] and returns a new path that starts at the same [start] as this path
     * and includes this path's last cell at the second last index and at the last index, the [given cell][cell].
     * If a new cell is added onto this path, it will not appear in the forked path's cell listing.
     * @param cell the cell to which this path should be forked to
     * @param at the index position at which this path should be forked; if this is negative, the path will be forked at the last element
     * @param cost the cost of traveling from the cell at forking of this path to [cell]
     * @return a new forked path from [at](included in this path) to [cell]
     * @throws IllegalArgumentException if [at] is greater than the length of this path
     * @throws IllegalArgumentException if [cell] is not a neighbor of the cell at [at]
     */
    fun forkTo(cell: Cell, cost: CellTravelCostSupplier, at: Int = -1): Path

    /**
     * Creates a new sub path that includes all cells from this path starting at [from] (inclusive) to [to] (inclusive).
     * @param from the index position at which the sub path should start
     * @param to the index position at which the sub path should end
     * @return a new sub path from [from] to [to]
     * @throws IllegalArgumentException if [from] is greater than [to]
     * @throws IllegalArgumentException if [from] is negative
     * @throws IllegalArgumentException if [to] or [from] are greater than the length of this path
     */
    fun subPath(from: Int, to: Int): Path

    /**
     * Creates a new sub path that includes all cells from this path starting at [from] (inclusive) to the last cell
     * (inclusive).
     * @param from the index position at which the sub path should start
     * @return a new sub path from [from] to the last cell
     * @throws IllegalArgumentException if [from] is negative
     * @throws IllegalArgumentException if [from] is greater than the length of this path
     */
    fun subPath(from: Int): Path = subPath(from, getLength() - 1)

    /**
     * Creates a new sub path that includes all cells from this path starting at the first cell (inclusive) to [to]
     * (inclusive).
     * @param to the index position at which the sub path should end
     * @return a new sub path from the first cell to [to]
     */
    fun subPathUntil(to: Int): Path = subPath(0, to)

    /**
     * Compares this path to the [other] path. The comparison is based on the [currentCost] of both paths.
     * @param other the other path to compare this path to
     * @return a negative integer, zero, or a positive integer as this path is less expensive than, equal to, or greater than the
     * [other] path
     */
    override fun compareTo(other: Path): Int {
        return currentCost.compareTo(other.currentCost)
    }

    object Empty : Path {

        override val start: Cell
            get() = throw NoSuchElementException("Empty path")
        override val end: Cell? = null
        override val currentCost: Int = Int.MAX_VALUE
        override var finished: Boolean = false

        override fun getCells(): List<Cell> = emptyList()

        override fun getLength(): Int = 0

        override fun getLast(): Cell = throw NoSuchElementException("Empty path")

        override fun forkTo(cell: Cell, cost: CellTravelCostSupplier, at: Int): Path = this

        override fun subPath(from: Int, to: Int): Path = this
    }
}