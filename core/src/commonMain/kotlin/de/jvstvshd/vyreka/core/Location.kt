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

package de.jvstvshd.vyreka.core

import kotlinx.serialization.Serializable
import kotlin.math.sqrt

/**
 * Represents a location in the map grid. The location is represented by its x, y and z coordinates.
 */
@Serializable
sealed interface Location {

    /**
     * Represents the x (length) coordinate of the location.
     */
    val x: Int

    /**
     * Represents the y (height) coordinate of the location.
     */
    val y: Int

    /**
     * Represents the z (width) coordinate of the location.
     */
    val z: Int

    operator fun plus(other: Location): Location
    operator fun minus(other: Location): Location
    operator fun times(other: Location): Location
    operator fun times(other: Int): Location = this * ImmutableLocation(other, other, other)
    operator fun div(other: Location): Location
    operator fun div(other: Int): Location = this / ImmutableLocation(other, other, other)
    operator fun rem(other: Location): Location
    operator fun rem(other: Int): Location = this % ImmutableLocation(other, other, other)

    /**
     * Calculates the squared distance between this location and another location. This is useful for comparing distances
     * without the need to calculate the square root. The squared distance is calculated as follows:
     * `(x2-x1 )^2 + (y2-y1)^2 + (z2-z1)^2`.
     *
     * @param other the other location to calculate the squared distance to
     */
    fun distanceSquared(other: Location): Int

    /**
     * Calculates the distance between this location and another location. This is useful for calculating the actual distance
     * between two locations. The distance is calculated as follows:
     * `sqrt((x2-x1 )^2 + (y2-y1)^2 + (z2-z1)^2)` and therefore yields the exact same result as the square root of [distanceSquared].
     * @param other the other location to calculate the distance to
     */
    fun distanceTo(other: Location): Double

    /**
     * Creates an immutable copy of this location. This is useful for ensuring that the location cannot be modified.
     * If any modification operations are called on the returned location, a new instance will be created but the original
     * location will remain unchanged.
     */
    fun immutable(): Location = ImmutableLocation(x, y, z)
}

interface Axis {
    val name: String
    object X : Axis {
        override val name: String = "x"
    }

    object Y : Axis {
        override val name: String = "y"
    }

    object Z : Axis {
        override val name: String = "z"
    }
}

@Serializable
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

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + z
        return result
    }
}

@Serializable
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

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + z
        return result
    }
}

/**
 * Modifies this location by applying the given [block] to it. The block is used to modify the location's x, y and z
 * coordinates. If this location is an instance of [MutableLocation], the coordinates will be modified directly. Otherwise,
 * a new instance of [ImmutableLocation] will be created with the modified coordinates.
 * @param block the block to apply to this location
 */
fun Location.modify(block: LocationBuilder.() -> Unit): Location {
    val builder = LocationBuilder(this)
    builder.block()
    if (this is MutableLocation) {
        x = builder.x
        y = builder.y
        z = builder.z
        return this
    }
    return builder.build()
}

/**
 * A builder for creating locations. This builder can be used to create immutable and mutable locations with the given
 * x, y and z coordinates.
 * @param startX the x coordinate to start with, defaults to 0
 * @param startY the y coordinate to start with, defaults to 0
 * @param startZ the z coordinate to start with, defaults to 0
 * @since 1.0.0
 */
class LocationBuilder(startX: Int = 0, startY: Int = 0, startZ: Int = 0) {

    /**
     * Creates a new location builder with the given [from] location.
     * @param from the location to start with
     * @see LocationBuilder
     */
    constructor(from: Location) : this(from.x, from.y, from.z)

    var x: Int = startX
    var y: Int = startY
    var z: Int = startZ

    /**
     * Builds an [ImmutableLocation] with the x, y and z coordinates that were set on this builder.
     */
    fun build(): Location {
        return ImmutableLocation(x, y, z)
    }

    fun buildMutable(): Location {
        return MutableLocation(x, y, z)
    }
}

/**
 * Creates a new immutable location with the given x, y and z coordinates.
 * @param x the x coordinate of the location
 * @param y the y coordinate of the location
 * @param z the z coordinate of the location
 * @return a new immutable location with the given x, y and z coordinates
 * @see ImmutableLocation
 * @since 1.0.0
 */
fun Location(x: Int, y: Int, z: Int): Location {
    return ImmutableLocation(x, y, z)
}

const val X_BITS = 20
const val Y_BITS = 20
const val Z_BITS = 20

const val X_MASK = (1L shl X_BITS) - 1
const val Y_MASK = (1L shl Y_BITS) - 1
const val Z_MASK = (1L shl Z_BITS) - 1

fun Location.encodeToLong(): Long {
    return ((x.toLong() and X_MASK) shl (Y_BITS + Z_BITS)) or
            ((y.toLong() and Y_MASK) shl Z_BITS) or
            (z.toLong() and Z_MASK)
}