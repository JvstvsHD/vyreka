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

/**
 * This interface represents an object that can be moved around in a 3-dimensional space. It extends [Locatable] and
 * adds a [location] property that can be changed. It also, at every point in time, is located at the location specified.
 * @since 1.0.0
 * @see Location
 * @see Locatable
 */
interface Movable : Locatable {

    /**
     * The location of the entity it is located at. This value is mutable and can be changed through the [move] method.
     */
    override val location: Location

    /**
     * Tries to move the entity to the specified [destination]. If the entity cannot be moved to the location, the method
     * returns a [MoveResult] that specifies whether the move was successful or not.
     * @param destination the location to move the entity to
     * @return a [MoveResult] that specifies whether the move was successful or not
     */
    suspend fun move(destination: Location): MoveResult

    /**
     * Represents the result of a move operation. This interface provides a [success] property that indicates whether
     * the move was successful or not. If the move was successful, [success] is true, otherwise it is false. The [type]
     * property aims at uniquely identifying the reason why the move was not successful. The following types are available:
     * - [MoveResult.Success] if the move was successful
     * - [MoveResult.LocationNotReachable] if the location is not reachable from the current location (no path can be found to there
     * or the destination itself is not accessible)
     * - [MoveResult.LocationOutOfBounds] if the location is out of the bounds of the map
     * - [MoveResult.UnspecifiedError] if the move failed due to an further unspecified error
     * - [MoveResult.Unknown] if the type of the move result is unknown
     * @since 1.0.0
     */
    interface MoveResult {
        val success: Boolean
        val type: Int

        companion object {
            val Unknown = object : MoveResult {
                override val success: Boolean = false
                override val type: Int = -1
            }
            val Success = object : MoveResult {
                override val success: Boolean = true
                override val type: Int = 0
            }
            val LocationNotReachable = object : MoveResult {
                override val success: Boolean = false
                override val type: Int = 1
            }

            val LocationOutOfBounds = object : MoveResult {
                override val success: Boolean = false
                override val type: Int = 2
            }

            val UnspecifiedError = object : MoveResult {
                override val success: Boolean = false
                override val type: Int = 3
            }

        }
    }
}