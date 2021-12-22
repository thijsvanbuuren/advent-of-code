package advent._2021

import advent.util.ADay
import advent.util.Day

@ADay(2021, 1, "day1")
class Day1 : Day() {

    override fun doPart1(input: List<String>): Any {
        val data = input.map { it.toInt() }

        var result = -1

        // first solution
        if (true) {
            var count = 0
            val iterator = data.iterator()
            var previous = iterator.next()
            while (iterator.hasNext()) {
                val current = iterator.next()
                if (current > previous) count++
                previous = current
            }
            result = count
        }
        // other
        if (false) {
            result = data
                .windowed(2) { it[0] < it[1] }
                .count { it }
        }

        // result of part 2
        if(false) {
            result = countWindowSize(1, data)
        }

        return result
    }

    override fun doPart2(input: List<String>): Any {
        val data = input.map { it.toInt() }

        return countWindowSize(3, data)
    }

    private fun countWindowSize(
        windowSize: Int,
        data: List<Int>
    ): Int {
        var count = 0
        (windowSize until data.size).forEach { index ->
            if (data[index] > data[index - windowSize]) count++
        }
        return count
    }
}
