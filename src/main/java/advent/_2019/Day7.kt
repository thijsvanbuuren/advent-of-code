package advent._2019

import advent.util.ADay
import advent.util.Day
import advent._2019.models.Util.createPossibilities
import advent._2019.opcode.OpcodeV2

@ADay(2019, 7, "day7")
class Day7 : Day {

    override fun doPart1(data: List<String>): Any {
        val program = parseInput(data)
        val input = 0L
        val possibilities: MutableList<List<Long>> = mutableListOf()
        val values = listOf(0L, 1L, 2L, 3L, 4L)

        createPossibilities(values, possibilities)

        return possibilities.map { runAmps(program, it, input) }.maxOrNull() ?: -1
    }

    override fun doPart2(data: List<String>): Any {
        val program = parseInput(data)
        val input = 0L
        val possibilities: MutableList<List<Long>> = mutableListOf()
        val values = listOf(5L, 6L, 7L, 8L, 9L)

        createPossibilities(values, possibilities)

        return possibilities.map { runAmpsLooped(program, it, input) }.maxOrNull() ?: -1
    }

    private fun runAmps(
        program: MutableList<Long>,
        phases: List<Long>,
        initialInput: Long
    ): Long {
        var input = initialInput
        for (i in phases.indices) {
            val amp = OpcodeV2(program, mutableListOf(phases[i], input), false)
            amp.process()
            input = amp.lastOutput()
        }
        return input
    }

    private fun runAmpsLooped(
        program: MutableList<Long>,
        phases: List<Long>,
        initialInput: Long
    ): Long {
        val amps = phases.indices.map { OpcodeV2(program, mutableListOf(phases[it]), false) }
        // passthrough
        phases.indices.forEach { amps[it].output = amps.getOrElse(it + 1, { amps[0] }).input }

        amps[0].input.add(initialInput)
        var i = 0
        while (true) {
//            Logger.info(i % phases.size)
            val amp = amps[i % phases.size]
            try {
                amp.process()
                if ((i % phases.size) == (phases.size - 1)) {
                    return amp.lastOutput()
                }
            } catch (e: Exception) {
//                Logger.info(e)
            }
            i++
        }
    }

    private fun parseInput(input: List<String>) = input[0].split(',')
        .map { it.toLong() }.toMutableList()

}