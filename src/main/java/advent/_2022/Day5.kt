package advent._2022

import advent.util.ADay
import advent.util.Day
import java.util.*

@ADay(2022, 5, "day5")
class Day5 : Day(false) {


    override fun doPart1(input: List<String>): Any {
        val stacks = buildList<Stack<String>> { repeat(10) { add(Stack()) } }
        // amount, from , to
        val moves = mutableListOf<Triple<Int, Int, Int>>()

        parseData(input, stacks, moves)

        stacks.forEach { log(it.toString()) }
        moves.onEach { log(it.toString()) }

        moves.forEach { move ->
            repeat(move.first) {
                stacks[move.third].push(stacks[move.second].pop())
            }
            stacks.forEach { log(it.toString()) }
        }

        stacks.forEach { log(it.toString()) }

        return stacks.filter { it.isNotEmpty() }.map { it.peek()[1].toString() }.reduce { a, b -> a + b }
    }

    private fun parseData(
        input: List<String>,
        stacks: List<Stack<String>>,
        moves: MutableList<Triple<Int, Int, Int>>
    ) {
        var parseMoves = false
        input.forEach { line ->
            if (line.isEmpty()) {
                parseMoves = true
                return@forEach
            }

            if (parseMoves.not()) {
                line.chunked(4)
                    .forEachIndexed { index, box ->
                        if (box.isBlank()) return@forEachIndexed
                        val stack = stacks.getOrElse(index) { Stack<String>() }
                        stack.push(box.trim())
                    }
            } else {
                val regex = "[0-9]{1,3}".toRegex()
                moves += regex.findAll(line).map { it.value.toInt() }.toList()
                    .let { Triple(it[0], it[1] - 1, it[2] - 1) }
            }
        }
        stacks.onEach { it.reverse() }
        // --------
    }

    override fun doPart2(input: List<String>): Any {

        val stacks = buildList<Stack<String>> { repeat(10) { add(Stack()) } }
        // amount, from , to
        val moves = mutableListOf<Triple<Int, Int, Int>>()

        parseData(input, stacks, moves)
        log("------part 2 ----------")
        moves.forEach { move ->
            (1..move.first).map {
                stacks[move.second].pop()
            }.reversed().forEach {
                stacks[move.third].push(it)
            }
            stacks.forEach { log(it.toString()) }
        }

        return stacks.filter { it.isNotEmpty() }.map { it.peek()[1].toString() }.reduce { a, b -> a + b }
    }
}