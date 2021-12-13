package advent._2021

import advent.util.ADay
import advent.util.Day
import org.slf4j.LoggerFactory

@ADay(2021, 2, "day2F")
class Day2F : Day {

    override fun doPart1(input: List<String>): Any {
        val x = input.filter { it.startsWith("forward") }
            .sumOf { Character.getNumericValue(it.last()) }

        val a = input.filter { it.startsWith("down") }
            .sumOf { Character.getNumericValue(it.last()) }

        val b = input.filter { it.startsWith("up") }
            .sumOf { Character.getNumericValue(it.last()) }

        logger.debug(" X: $x   A: $a   B: $b")

        return x * (a - b)
    }

    override fun doPart2(input: List<String>): Any {

        var x = 0
        var y = 0
        var aim = 0

        input.forEach {
            val getal = Character.getNumericValue(it.last())
            if (it.startsWith("forward")) {
                x += getal
                y += (getal * aim)
            }
            if (it.startsWith("down")) {
                aim += getal
            }
            if (it.startsWith("up")) {
                aim -= getal
            }
//            logger.debug(" X: $x   Y: $y   AIM: $aim")
        }

        return x * y
    }
}