package advent._2019

import advent.util.ADay
import advent.util.Day
import advent._2019.opcode.OpcodeV2

@ADay(2019, 9, "day9")
class Day9 : Day() {

    override fun doPart1(data: List<String>): Any {
        val program = parseInput(data)
        val opcodeV2 = OpcodeV2(program, mutableListOf(1), false)
        opcodeV2.process()
        return opcodeV2.lastOutput()
    }

    override fun doPart2(data: List<String>): Any {
        val program = parseInput(data)
        val opcodeV2 = OpcodeV2(program, mutableListOf(2), false)
        opcodeV2.process()
        return opcodeV2.lastOutput()
    }

    private fun parseInput(input: List<String>) = input[0].split(',')
        .map { it.toLong() }.toMutableList()
}