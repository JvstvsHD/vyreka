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

package de.jvstvshd.vyreka.core.attribute

import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 * A key is a unique identifier for an attribute. It is used to store, retrieve and identify attributes.
 * Keys are immutable and should be unique.
 * @see StringKey
 * @see IntKey
 * @since 1.0.0
 */
interface Key {

    /**
     * Returns the type of the attribute that is associated with this key.
     */
    fun type(): KType

    companion object {
        /**
         * Creates a key that is identified by a string.
         * @param key the string that identifies this key
         * @return a key that is identified by a string
         */
        fun fromString(key: String): Key {
            return StringKey(key)
        }

        /**
         * Creates a key that is identified by a long.
         * @param key the long that identifies this key
         * @return a key that is identified by a long
         */
        fun fromLong(key: Long): Key {
            return LongKey(key)
        }
    }
}

/**
 * A key that is identified by a string.
 * @param key the string that identifies this key
 * @since 1.0.0
 */
data class StringKey(val key: String) : Key {

    override fun type(): KType {
        return typeOf<String>()
    }

    override fun toString(): String {
        return key
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StringKey) return false
        return key == other.key
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }
}

/**
 * A key that is identified by a long.
 * @param key the long that identifies this key
 * @since 1.0.0
 */
data class LongKey(val key: Long) : Key {
    override fun type(): KType {
        return typeOf<Int>()
    }

    override fun toString(): String {
        return key.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LongKey) return false
        return key == other.key
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }
}