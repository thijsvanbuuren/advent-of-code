package advent._2019

import advent.util.ADay
import advent.util.Day
import advent._2019.opcode.OpcodeV2

@ADay(2019, 5, "day5")
class Day5 : Day {

    override fun doPart1(input: List<String>): Any {
        val data = parseInput(input)

        val opcodeV2 = OpcodeV2(data, mutableListOf(1), false)
        opcodeV2.process()
        return opcodeV2.lastOutput()
    }

    override fun doPart2(input: List<String>): Any {
        val data = parseInput(input)

        val opcodeV2 = OpcodeV2(data, mutableListOf(5), false)
        opcodeV2.process()
        return opcodeV2.lastOutput()
    }

    private fun parseInput(input: List<String>) = input[0].split(',')
        .map { it.toLong() }.toMutableList()
}