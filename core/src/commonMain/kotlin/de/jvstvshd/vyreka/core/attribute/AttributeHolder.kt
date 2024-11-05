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

interface AttributeHolder {

    /**
     * Sets an attribute with the given key to the given value. If an attribute with the same key already exists, it will be
     * overwritten.
     * @param key the key of the attribute
     * @param value the value of the attribute
     */
    fun setAttribute(key: Key, value: Any)

    /**
     * Removes an attribute with the given key. If no attribute with the given key exists, null is returned; otherwise
     * the previous value that was associated with the key is returned.
     * @param key the key of the attribute
     * @return the previous value that was associated with the key, or null if no attribute with the key existed
     */
    fun removeAttribute(key: Key): Any?

    /**
     * Retrieves an attribute with the given key. If no attribute with the given key exists, null is returned.
     * @param key the key of the attribute
     */
    fun getAttributeOrNull(key: Key): Any?

    /**
     * Retrieves an attribute with the given key. If no attribute with the given key exists, an exception is thrown.
     * @param key the key of the attribute
     * @throws NoSuchElementException if no attribute with the given key exists
     */
    fun getAttribute(key: Key): Any =
        getAttributeOrNull(key) ?: throw NoSuchElementException("No attribute with key $key found.")

    /**
     * Retrieves an attribute with the given key. If no attribute with the given key exists, the default value is returned.
     * @param key the key of the attribute
     * @param default the default value
     * @return the attribute with the given key, or the default value if no attribute with the key exists
     */
    fun getAttributeOrDefault(key: Key, default: Any): Any = getAttributeOrNull(key) ?: default

    /**
     * Whether this cell has an attribute with the given key.
     */
    fun hasAttribute(key: Key): Boolean = getAttributeOrNull(key) != null

    /**
     * Returns a set of all keys that are associated with an attribute.
     */
    fun getKeys(): Set<Key>

    /**
     * Returns a map of all attributes that are associated with a key which can be modified.
     */
    fun getAttributes(): Map<Key, Any>
}

/**
 * Retrieves an attribute with the given key and casts it to the given type. If the attribute is not of the given type,
 * a [ClassCastException] is thrown. If no attribute with the given key exists, an exception is thrown.
 * @param key the key of the attribute
 * @param T the type of the attribute
 * @throws ClassCastException if the attribute is not of the given type
 * @throws NoSuchElementException if no attribute with the given key exists
 * @return the attribute with the given key, cast to the given type
 */
inline fun <reified T> AttributeHolder.getAttributeType(key: Key): T {
    val attribute = getAttribute(key)
    if (!T::class.isInstance(attribute)) {
        throw ClassCastException("Attribute with key $key is not of type ${T::class.simpleName}.")
    }
    return attribute as T
}

/**
 * Retrieves an attribute with the given key and casts it to the given type. If the attribute is not of the given type,
 * a [ClassCastException] is thrown. If no attribute with the given key exists, null is returned.
 * @param key the key of the attribute
 * @param T the type of the attribute
 * @throws ClassCastException if the attribute is not of the given type
 * @return the attribute with the given key, cast to the given type, or null if no attribute with the key exists
 */
inline fun <reified T> AttributeHolder.getAttributeTypeOrNull(key: Key): T? {
    val attribute = getAttributeOrNull(key)
    if (attribute != null && !T::class.isInstance(attribute)) {
        throw ClassCastException("Attribute with key $key is not of type ${T::class.simpleName}.")
    }
    return attribute as T?
}

/**
 * Retrieves an attribute with the given key and casts it to the given type. If the attribute is not of the given type,
 * a [ClassCastException] is thrown. If no attribute with the given key exists, the default value is returned.
 * @param key the key of the attribute
 * @param default the default value
 * @param T the type of the attribute
 * @throws ClassCastException if the attribute is not of the given type
 * @return the attribute with the given key, cast to the given type, or the default value if no attribute with the key exists
 */
inline fun <reified T> AttributeHolder.getAttributeTypeOrDefault(key: Key, default: T): T {
    val attribute = getAttributeTypeOrNull<T>(key)
    if (!T::class.isInstance(attribute)) {
        throw ClassCastException("Attribute with key $key is not of type ${T::class.simpleName}.")
    }
    if (attribute == null) {
        return default
    }
    return attribute
}