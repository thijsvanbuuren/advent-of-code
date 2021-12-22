package advent._2019

import advent.util.ADay
import advent.util.Day

@ADay(2019, 4, "day4")
class Day4 : Day() {

    override fun doPart1(input: List<String>): Any {
        val data = input[0].split('-')
        val start = data[0].toInt()
        val end = data[1].toInt()

        return count(start, end, this::isValid)
    }

    override fun doPart2(input: List<String>): Any {
        val data = input[0].split('-')
        val start = data[0].toInt()
        val end = data[1].toInt()

        return count(start, end, this::isMoreValid)
    }

    private fun count(start: Int, end: Int, isValid: (number: Int) -> Boolean): Int {
        var counter = 0
        var number = toFirstPossibleNumber(start)
        while (number <= end) {
            if (isValid(number)) {
                counter++
            }
            number = toFirstPossibleNumber(number + 1)
        }
        return counter
    }

    private fun toFirstPossibleNumber(number: Int): Int {
        val text = number.toString()
        var previous: Int? = null
        for (i in text.indices) {
            val current = text[i].toString().toInt()
            if (previous ?: 0 > current) {
                return text.substring(0, i).padEnd(6, previous!!.toString()[0]).toInt()
            }
            previous = current
        }
        return number
    }

    private fun isValid(number: Int): Boolean {
        var hasAPair = false
        var previous: Char? = null
        number.toString().forEach {
            if (it == previous) {
                hasAPair = true
            }
            previous = it
        }
        return hasAPair
    }

    private fun isMoreValid(number: Int): Boolean {
        var hasAPair = false
        var previous: Int? = null
        val value = number.toString()
        var previousCount = 0
        for (index in value.indices) {
            val current = value[index].toString().toInt()
            if (current == previous) {
                previousCount++
                val next = value.getOrNull(index + 1)?.toString()?.toInt() ?: -1
                if (previousCount == 1 && next != current) {
                    hasAPair = true
                }
            } else {
                previousCount = 0
            }
            previous = current
        }
        return hasAPair
    }
}