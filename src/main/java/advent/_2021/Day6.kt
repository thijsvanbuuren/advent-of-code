package advent._2021

import advent.util.ADay
import advent.util.Day
import java.util.*

@ADay(2021, 6, "day6")
class Day6 : Day {

    override fun doPart1(input: List<String>): Any {
        val initialFishCount = buildInitialFishCount(input)

        val fishCount: MutableList<Long> = initialFishCount
        runSim(80, fishCount)

        return fishCount.sumOf { it }
    }

    override fun doPart2(input: List<String>): Any {
        val initialFishCount = buildInitialFishCount(input)

        val fishCount: MutableList<Long> = initialFishCount
        runSim(256, fishCount)

        return fishCount.sumOf { it }
    }

    private fun runSim(days: Int, fishCount: MutableList<Long>) {
        repeat(days) {
            Collections.rotate(fishCount, -1)
            fishCount[6] = fishCount[6] + fishCount[8]
        }
    }

    private fun buildInitialFishCount(input: List<String>): MutableList<Long> {
        val initialFishCount = longArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0).toMutableList()
        input.first().split(",").forEach {
            val key = it.toInt()
            initialFishCount[key] = initialFishCount[key] + 1
        }
        return initialFishCount
    }
}