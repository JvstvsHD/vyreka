package de.jvstvshd.vyreka.path

expect class PriorityQueue<T : Comparable<T>>(): AbstractMutableCollection<T> {

    fun poll(): T

    override val size: Int
    override fun add(element: T): Boolean
    override fun iterator(): MutableIterator<T>
}