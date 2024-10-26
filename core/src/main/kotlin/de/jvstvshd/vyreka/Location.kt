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

package de.jvstvshd.vyreka

import kotlin.math.sqrt

/**
 * Represents a location in the map.
 * The locations are represented in 3D since there may be multiple 2-dimensional layers.
 * The y-axis is used to represent the layer.
 */
interface Location {

    /**
     * Represents the x (length) coordinate of the location, per layer.
     */
    val x: Int

    /**
     * Represents the y (height) coordinate of the location, i.e. the layer.
     */
    val y: Int

    /**
     * Represents the z (width) coordinate of the location, per layer.
     */
    val z: Int

    operator fun plus(other: Location): Location
    operator fun minus(other: Location): Location
    operator fun times(other: Location): Location
    operator fun div(other: Location): Location
    operator fun rem(other: Location): Location
    fun distanceSquared(other: Location): Int
    fun distanceTo(other: Location): Double
    fun immutable(): Location = ImmutableLocation(x, y, z)
}

data class ImmutableLocation(override val x: Int, override val y: Int, override val z: Int) : Location {

    override operator fun plus(other: Location): Location {
        return ImmutableLocation(x + other.x, y + other.y, z + other.z)
    }

    override operator fun minus(other: Location): Location {
        return ImmutableLocation(x - other.x, y - other.y, z - other.z)
    }

    override operator fun times(other: Location): Location {
        return ImmutableLocation(x * other.x, y * other.y, z * other.z)
    }

    override operator fun div(other: Location): Location {
        return ImmutableLocation(x / other.x, y / other.y, z / other.z)
    }

    override operator fun rem(other: Location): Location {
        return ImmutableLocation(x % other.x, y % other.y, z % other.z)
    }

    override fun distanceSquared(other: Location): Int {
        return (x - other.x).pow(2) + (y - other.y).pow(2) + (z - other.z).pow(2)
    }

    override fun distanceTo(other: Location): Double {
        return sqrt(distanceSquared(other).toDouble())
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Location) {
            return false
        }
        return x == other.x && y == other.y && z == other.z
    }
}

data class MutableLocation(override var x: Int, override var y: Int, override var z: Int) : Location {

    override operator fun plus(other: Location): Location {
        x += other.x
        y += other.y
        z += other.z
        return this
    }

    override operator fun minus(other: Location): Location {
        x -= other.x
        y -= other.y
        z -= other.z
        return this
    }

    override operator fun times(other: Location): Location {
        x *= other.x
        y *= other.y
        z *= other.z
        return this
    }

    override operator fun div(other: Location): Location {
        x /= other.x
        y /= other.y
        z /= other.z
        return this
    }

    override operator fun rem(other: Location): Location {
        x %= other.x
        y %= other.y
        z %= other.z
        return this
    }

    override fun distanceSquared(other: Location): Int {
        return (x - other.x).pow(2) + (y - other.y).pow(2) + (z - other.z).pow(2)
    }

    override fun distanceTo(other: Location): Double {
        return sqrt(distanceSquared(other).toDouble())
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Location) {
            return false
        }
        return x == other.x && y == other.y && z == other.z
    }
}

fun Location.modify(block: LocationBuilder.() -> Unit) {
    if (this !is MutableLocation) {
        throw UnsupportedOperationException("Cannot modify an immutable location")
    }
    val builder = LocationBuilder(this)
    builder.block()
    x = builder.x
    y = builder.y
    z = builder.z
}

class LocationBuilder(startX: Int = 0, startY: Int = 0, startZ: Int = 0) {
    constructor(from: Location) : this(from.x, from.y, from.z)

    var x: Int = startX
    var y: Int = startY
    var z: Int = startZ

    fun build(): Location {
        return ImmutableLocation(x, y, z)
    }

    fun buildMutable(): Location {
        return MutableLocation(x, y, z)
    }
}

class LayerlessLocation(val location: Location) : Location by location {
    override fun equals(other: Any?): Boolean {
        if (other !is Location) {
            return false
        }
        return location.x == other.x && location.z == other.z
    }
}