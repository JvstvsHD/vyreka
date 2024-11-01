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

package de.jvstvshd.vyreka.core.cell

import de.jvstvshd.vyreka.core.Axis
import de.jvstvshd.vyreka.core.ImmutableLocation
import de.jvstvshd.vyreka.core.Location
import de.jvstvshd.vyreka.core.attribute.SimpleAttributeHolder
import de.jvstvshd.vyreka.core.attribute.getAttributeType
import de.jvstvshd.vyreka.core.attribute.getAttributeTypeOrDefault
import de.jvstvshd.vyreka.core.map.VyrekaMap
import kotlin.math.abs

/**
 * A simple implementation of a [Cell] that is used in a grid-based map.
 * @param location the location of this cell
 * @param map the map this cell is located on
 */
open class SimpleGridCell(override val location: Location, override val map: VyrekaMap) : Cell, SimpleAttributeHolder(mutableMapOf()) {

    override fun getNeighbors(mode: CellAccessMode, includeDiagonals: Boolean, vararg excludeAxes: Axis): List<Cell> {
        val neighbours = mutableListOf<Cell>()
        val excludedAxesList = excludeAxes.toList()
        for (x in -1..1) {
            for (y in -1..1) {
                for (z in -1..1) {
                    if (x == 0 && y == 0 && z == 0) {
                        continue
                    }
                    if (Axis.X in excludedAxesList && x != 0 || Axis.Y in excludedAxesList && y != 0 || Axis.Z in excludedAxesList && z != 0) {
                        continue
                    }
                    if (!includeDiagonals && (abs(x) + abs(y) + abs(z) > 1)) {
                        continue
                    }
                    val neighbourLocation = ImmutableLocation(location.x + x, location.y + y, location.z + z)
                    val neighbour = map.getCellAtOrNull(neighbourLocation) ?: continue
                    when (mode) {
                        CellAccessMode.ACCESSIBLE -> {
                            if (neighbour.canBeAccessedFrom(this)) {
                                neighbours.add(neighbour)
                            }
                        }

                        CellAccessMode.INACCESSIBLE -> {
                            if (!neighbour.canBeAccessedFrom(this)) {
                                neighbours.add(neighbour)
                            }
                        }

                        CellAccessMode.ALL -> {
                            neighbours.add(neighbour)
                        }
                    }
                }
            }
        }
        return neighbours
    }

    override fun canBeAccessedFrom(other: Cell): Boolean {
        if (other.map != map) {
            throw IllegalArgumentException("The other cell is not on the same map as this cell.")
        }
        if (!getAttributeTypeOrDefault<Boolean>(Cell.PermeabilityKey, true)) return false
        if (other.location.distanceSquared(location) != 1) return false
        return other.getAttributeTypeOrDefault<Boolean>(Cell.PermeabilityKey, true)
    }
}