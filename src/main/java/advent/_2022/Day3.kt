package advent._2022

import advent.util.ADay
import advent.util.Day

@ADay(2022, 3, "day3")
class Day3 : Day(false) {

    override fun doPart1(input: List<String>): Any {
        log("A" + 'A'.code)
        log("a" + 'a'.code)
        return input.map {
            val count = it.length / 2
            it.substring(0, count) to it.substring(count)
        }.map {
            it.first.toSet().intersect(it.second.toSet())
        }
            .onEach { log(it.toString() + ":" + it.map { it.toPriority() }) }
            .map { it.sumOf { it.toPriority() } }
            .sum()
    }

    fun Char.toPriority() = this.code - if (this.isUpperCase()) 38 else 96

    override fun doPart2(input: List<String>): Any {
        return input.chunked(3).map {
            it.map { it.toSet() }.reduce { a, b -> a.intersect(b) }
        }.onEach {
            log(it.first().toPriority().toString())
        }.map { it.first().toPriority() }
            .sum()
    }
}