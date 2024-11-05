package de.jvstvshd.vyreka.path

actual class PriorityQueue<T : Comparable<T>> : AbstractMutableCollection<T>() {
    private val heap = mutableListOf<T>()

    override fun isEmpty(): Boolean {
        return heap.isEmpty()
    }

    actual override val size: Int
        get() = heap.size

    actual override fun add(element: T): Boolean {
        val ret = heap.add(element)
        siftUp(heap.size - 1)
        return ret
    }

    actual fun poll(): T {
        if (heap.isEmpty()) throw NoSuchElementException("PriorityQueue is empty.")
        val result = heap[0]
        val last = heap.removeAt(heap.size - 1)
        if (heap.isNotEmpty()) {
            heap[0] = last
            siftDown(0)
        }
        return result
    }

    actual override fun iterator(): MutableIterator<T> {
        return heap.iterator()
    }

    private fun siftUp(index: Int) {
        var i = index
        while (i > 0) {
            val parent = (i - 1) / 2
            if (heap[i] >= heap[parent]) break
            heap.swap(i, parent)
            i = parent
        }
    }

    private fun siftDown(index: Int) {
        var i = index
        while (true) {
            val left = 2 * i + 1
            val right = 2 * i + 2
            var smallest = i
            if (left < heap.size && heap[left] < heap[smallest]) smallest = left
            if (right < heap.size && heap[right] < heap[smallest]) smallest = right
            if (smallest == i) break
            heap.swap(i, smallest)
            i = smallest
        }
    }

    private fun MutableList<T>.swap(i: Int, j: Int) {
        val temp = this[i]
        this[i] = this[j]
        this[j] = temp
    }

}