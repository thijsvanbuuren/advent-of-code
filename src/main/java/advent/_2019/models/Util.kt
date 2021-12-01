package advent._2019.models

import java.util.*

object Util {

    fun createPossibilities(
        values: List<Long>,
        possibilities: MutableList<List<Long>>,
        used: Stack<Long> = Stack(),
        possibility: Array<Long?> = arrayOfNulls<Long?>(values.size)
    ) {
        for (it in values) {
            if (used.contains(it)) continue
            possibility[used.size] = it
            if (used.size == (values.size - 1)) {
                possibilities.add(possibility.filterNotNull())
            } else {
                used.push(it)
                createPossibilities(values, possibilities, used, possibility)
                used.pop()
            }
        }
    }
}