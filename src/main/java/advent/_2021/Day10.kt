package advent._2021

import advent.util.ADay
import advent.util.Day
import org.slf4j.LoggerFactory
import java.util.*

@ADay(2021, 10, "day10")
class Day10 : Day {

    val openers = listOf('(', '[', '{', '<')
    val closers = listOf(')', ']', '}', '>')
    override fun doPart1(input: List<String>): Any {
        return input.mapNotNull {
            checkLine(it)
        }.sumOf {
            when (it) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> 0
            } as Int
        }
    }

    private fun checkLine(line: String): Char? {
        val stack = Stack<Char>()
        line.forEach {
            if (openers.contains(it)) {
                stack.push(it)
            } else {
                val shouldBe = closers[openers.indexOf(stack.pop())]
                if (it != shouldBe) {
//                    logger.debug("Error:\n$line\nExpected $shouldBe is $it")
                    return it
                }
            }
        }
        return null
    }

    override fun doPart2(input: List<String>): Any {
        return input.filter {
            checkLine(it) == null
        }.map {
            var first = false

            fixLine(it).map { closers.indexOf(it) + 1L }.reduce { a, b ->
                val total: Long = a * 5L + b
//                logger.debug(" $a * 5 + $b = $total")
                total
            }
        }.sorted().let { it[it.size / 2] }
    }

    private fun fixLine(line: String): List<Char> {
        val stack = Stack<Char>()
        line.forEach {
            if (openers.contains(it)) {
                stack.push(it)
            } else {
                stack.pop()
            }
        }
        val result = stack.toList().reversed().map { closers[openers.indexOf(it)] }
//        logger.debug("Missing for :\n$line\nAre $result")
        return result
    }
}