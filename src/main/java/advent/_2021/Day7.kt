package advent._2021

import advent.util.ADay
import advent.util.Day
import org.slf4j.LoggerFactory

@ADay(2021, 7, "day7")
class Day7 : Day {

    val logger = LoggerFactory.getLogger("Day 7")

    override fun doPart1(input: List<String>): Any {
        val data = input.first().split(",").map { it.toInt() }

        return runData(data, ::calculateFuel)
    }


    private fun calculateFuel(data: List<Int>, avg: Int) =
        data.sumOf { if (avg > it) avg - it else it - avg }

    override fun doPart2(input: List<String>): Any {
        val data = input.first().split(",").map { it.toInt() }

        return runData(data, ::calculateFuelPart2)
    }


    private fun calculateFuelPart2(data: List<Int>, avg: Int) =
        data.sumOf {
            val steps = (if (avg > it) avg - it else it - avg)

            steps.times(steps + 1).div(2)
        }

    private fun runData(
        data: List<Int>,
        calculator: (List<Int>, Int) -> Int
    ): Int {
        val avg = data.sumOf { it }.div(data.size)
        val min = data.minOf { it }
        val max = data.maxOf { it }

        var nr = -1
        var fuel = Int.MAX_VALUE
        (min..max).forEach {
            var nFuel = calculator(data, it)
            if (nFuel < fuel) {
                fuel = nFuel
                nr = it
            }
        }

        logger.debug(" nr: ${nr}")
        return fuel
    }
}