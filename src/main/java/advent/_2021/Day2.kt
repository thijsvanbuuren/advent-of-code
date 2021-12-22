package advent._2021

import advent.util.ADay
import advent.util.Day

@ADay(2021, 2, "day2")
class Day2 : Day() {
    override fun doPart1(input: List<String>): Any {
        val sub = Submarine()

        input.forEach { sub.doStep(it) }

        return sub.result()
    }

    override fun doPart2(input: List<String>): Any {
        val sub = SubmarineV2()

        input.forEach { sub.doStep(it) }

        return sub.result()
    }
}