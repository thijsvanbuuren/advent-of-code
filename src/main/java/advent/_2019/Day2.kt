package advent._2019

import advent.util.ADay
import advent.util.Day
import advent._2019.opcode.OpcodeV1

@ADay(2019, 2, "day2")
class Day2 : Day {

    override fun doPart1(input: List<String>): Any {
        val intInput = parseInput(input)

        return OpcodeV1.process(intInput)[0]
//        return OpcodeV2(intInput, 0, false).process()[0]
    }

    override fun doPart2(input: List<String>): Any {
        val intInput = parseInput(input)
        return intInput.run { findResult(this, 19690720) }
    }

    private fun parseInput(input: List<String>): MutableList<Int> {
        val intInput = input[0].split(',')
            .map { it.toInt() }.toMutableList()
        intInput[1] = 12
        intInput[2] = 2
        return intInput
    }

    private fun findResult(values: MutableList<Int>, targetResult: Int): List<Int> {
        for (noun in 0..99) {
            for (verb in 0..99) {
                val newSet = (ArrayList<Int>() + values).toMutableList()
                newSet[1] = noun
                newSet[2] = verb
                if (OpcodeV1.process(newSet)[0] == targetResult) {
                    return arrayListOf(noun, verb)
                }
            }
        }
        return emptyList()
    }
}