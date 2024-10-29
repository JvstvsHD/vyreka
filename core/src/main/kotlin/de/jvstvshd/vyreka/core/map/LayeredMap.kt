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

/**
 * This class represents a map in a 3-dimensional grid that contains multiple layers. Each layer is a 2-dimensional grid
 * in which each cell has the same y-coordinate. The layers are ordered by their y-coordinate, with the first layer having
 * the lowest y-coordinate and the last layer having the highest y-coordinate.
 * @since 1.0.0
 * @see VyrekaMap
 * @see MapLayer
 */
interface LayeredMap : VyrekaMap {

    /**
     * Returns a list of all layers in this map. The layers are ordered by their y-coordinate, with the first layer having
     * the lowest y-coordinate and the last layer having the highest y-coordinate.
     */
    fun getLayers(): List<MapLayer>

    /**
     * Returns the layer at the specified index. The [layer] is the y-coordinate of the layer that should be returned.
     * @param layer the y-coordinate of the layer to return
     * @return the layer at the specified index
     * @throws NoSuchElementException if there is no layer at the specified index
     */
    fun getLayer(layer: Int): MapLayer

    /**
     * Sets the layer at the specified index to the given [layer]. If there is already a layer at the specified index, it
     * is replaced by the new layer. If the specified index is out of bounds, an exception is thrown. This method will not
     * modify the order of the layers and the height of the map.
     * @param layer the layer to set
     * @return the index of the layer that was set
     * @throws IndexOutOfBoundsException if the specified index is out of bounds
     */
    fun setLayer(layer: MapLayer): Int
}