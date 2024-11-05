package de.jvstvshd.vyreka.path

actual class PriorityQueue<T : Comparable<T>> : AbstractMutableCollection<T>() {

    val delegateQueue = java.util.PriorityQueue<T>()

    override fun isEmpty(): Boolean {
        return delegateQueue.isEmpty()
    }

    actual override val size: Int
        get() = delegateQueue.size

    actual override fun add(element: T): Boolean {
        return delegateQueue.add(element)
    }

    actual fun poll(): T {
        return delegateQueue.poll()
    }

    actual override fun iterator(): MutableIterator<T> {
        return delegateQueue.iterator()
    }
}