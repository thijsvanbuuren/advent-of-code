package advent._2022

import advent.util.ADay
import advent.util.Day


@ADay(2022, 4, "day4")
class Day4 : Day() {

    override fun doPart1(input: List<String>): Any {
        return input.map {
            val ranges = it.split(",").map {
                it.split("-").map { it.toInt() }.let { IntRange(it[0], it[1]) }
            }
            ranges[0] to ranges[1]
        }.onEach { log(it.toString()) }
            .filter { (left, right) ->
                (right.contains(left.first) && right.contains(left.last))
                        || (left.contains(right.first) && left.contains(right.last))
            }.size
    }

    override fun doPart2(input: List<String>): Any {
        return input.map {
            val ranges = it.split(",").map {
                it.split("-").map { it.toInt() }.let { IntRange(it[0], it[1]) }
            }
            ranges[0] to ranges[1]
        }.onEach { log(it.toString()) }
            .filter { (left, right) ->
                (right.contains(left.first) || right.contains(left.last))
                        || (left.contains(right.first) || left.contains(right.last))
            }.size
    }
}