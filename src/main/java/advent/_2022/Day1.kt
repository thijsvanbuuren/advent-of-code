package advent._2022

import advent.util.ADay
import advent.util.ParsedDay
import java.util.*

@ADay(2022, 1, "day1")
class Day1 : ParsedDay<Stack<Elf>>({ input ->
    val stack = Stack<Elf>()
    stack.push(Elf())
    input.forEach { line ->
        if (line.isEmpty()) {
            stack.push(Elf())
            return@forEach
        }

        stack.peek().addCalories(line.toInt())
    }
    stack
}) {
    override fun doPart1Parsed(input: Stack<Elf>): Any {
        return input.maxOf { it.calories }
    }

    override fun doPart2Parsed(input: Stack<Elf>): Any {
        input.sortByDescending { it.calories }
        return input.take(3).sumOf { it.calories }
    }
}


class Elf {

    var calories = 0
        private set

    var productCount = 0
        private set

    fun addCalories(cals: Int) {
        calories += cals
        productCount++
    }
}
