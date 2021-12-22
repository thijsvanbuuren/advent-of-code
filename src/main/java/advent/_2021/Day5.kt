package advent._2021

import advent.util.ADay
import advent.util.Day

@ADay(2021, 5, "day5")
class Day5 : Day() {

    override fun doPart1(input: List<String>): Any {
        val pairs = buildPairs(input)

        val countMap = countCrossed(pairs)

        return countMap.values.filter { it >= 2 }.count()
    }

    override fun doPart2(input: List<String>): Any {
        val pairs = buildPairs(input)

        val countMap = countCrossed(pairs, true)

        return countMap.values.filter { it >= 2 }.count()
    }

    private fun buildPairs(input: List<String>) = input.map {
        it.split(" -> ").map {
            it.split(",").let { it[0].toInt() to it[1].toInt() }
        }.let { it[0] to it[1] }
    }

    private fun countCrossed(
        pairs: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>,
        includeCross: Boolean = false
    ): MutableMap<Pair<Int, Int>, Int> {
        val countMap = mutableMapOf<Pair<Int, Int>, Int>()
        pairs.forEach {
            val from = it.first
            val to = it.second
            if (from.first != to.first && from.second == to.second) {
                buildRange(from.first, to.first).forEach { x ->
                    val key = (x to from.second)
                    countMap[key] = (countMap[key] ?: 0) + 1
                }
            } else if (from.second != to.second && from.first == to.first) {
                buildRange(from.second, to.second).forEach { y ->
                    val key = (from.first to y)
                    countMap[key] = (countMap[key] ?: 0) + 1
                }
            } else if (includeCross) {
                val xRange = buildRange(from.first, to.first).iterator()
                val yRange = buildRange(from.second, to.second).iterator()
                while (xRange.hasNext() && yRange.hasNext()) {
                    val key = (xRange.next() to yRange.next())
                    countMap[key] = (countMap[key] ?: 0) + 1
                }
            }
        }
        return countMap
    }

    private fun buildRange(first: Int, second: Int): IntProgression {
        return if (first < second) first.rangeTo(second)
        else first.downTo(second)
    }
}