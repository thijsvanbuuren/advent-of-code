package advent._2019

import advent.util.ADay
import advent.util.Day
import java.math.BigInteger

@ADay(2019, 1, "day1")
class Day1 : Day() {
    private val divider = BigInteger.valueOf(3)

    override fun doPart1(input: List<String>): Any {
        return input
            .map { it.toBigInteger() }
            .map { calculateFuel(it) }
            .sumBy { it.toInt() }
    }

    override fun doPart2(input: List<String>): Any {
        return input
            .map { it.toBigInteger() }
            .map { calculateFuelIncludingFuel(it) }
            .sumBy { it.toInt() }
    }

    private fun calculateFuelIncludingFuel(mass: BigInteger): BigInteger {
        val fuel = calculateFuel(mass)
        if (fuel <= BigInteger.ZERO) {
            return mass
        }
        return fuel.add(calculateFuelIncludingFuel(fuel))
    }

    private fun calculateFuel(it: BigInteger) = it.div(divider).subtract(BigInteger.valueOf(2))
}