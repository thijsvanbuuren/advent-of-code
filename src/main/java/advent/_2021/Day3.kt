package advent._2021

import advent.util.ADay
import advent.util.Day
import org.slf4j.LoggerFactory

@ADay(2021, 3, "day3")
class Day3 : Day() {

    override fun doPart1(input: List<String>): Any {
        val halfSize = input.size / 2
        val counts = input.map { it.toList().map { Character.getNumericValue(it) } }
            .reduce { a, b -> a.mapIndexed { index, v -> v + b[index] } }

        val gamma = counts.map { if (it > halfSize) "1" else "0" }
        val epsilon = gamma.map { if (it == "1") "0" else "1" }

        val countsSTr = counts.joinToString("")
        val gammaStr = gamma.joinToString("")
        val gammaInt = gammaStr.toInt(2)
        val epsilonStr = epsilon.joinToString("")
        val epsilonInt = epsilonStr.toInt(2)

        log(" COUNTS: $countsSTr ")
        log(" GAMMA: $gammaStr -> $gammaInt")
        log(" EPSILON: $epsilonStr -> $epsilonInt ")

        return gammaInt * epsilonInt
    }

    override fun doPart2(input: List<String>): Any {
        val data = input.map { it.toList().map { Character.getNumericValue(it) } }

        val oxy = findValue(data, true)
        val co2 = findValue(data, false)

        log(" OXY: $oxy")
        log(" CO2: $co2")

        return oxy * co2
    }

    private fun findValue(
        data: List<List<Int>>,
        mostCommon: Boolean
    ): Int {
        var index = 0
        var subList = data
        while (subList.size > 1) {
            subList = findSubList(subList, index, mostCommon)
            index++
        }
        return subList.first().joinToString("").toInt(2)
    }

    private fun findSubList(
        input: List<List<Int>>,
        index: Int,
        mostCommon: Boolean
    ): List<List<Int>> {
        val list0 = mutableListOf<List<Int>>()
        val list1 = mutableListOf<List<Int>>()
        input.forEach {
            (if (it[index] == 0) list0 else list1) += it
        }

        return if (mostCommon) {
            when {
                list0.size > list1.size -> list0
                list0.size < list1.size -> list1
                else -> list1
            }
        } else when {
            list0.size < list1.size -> list0
            list0.size > list1.size -> list1
            else -> list0
        }
    }
}