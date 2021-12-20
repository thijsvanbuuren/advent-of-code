package advent._2021

import advent.util.ADay
import advent.util.Day
import org.slf4j.LoggerFactory
import kotlin.math.absoluteValue

@ADay(2021, 17, "day17")
class Day0 : Day {
    override fun doPart1(input: List<String>): Any {
//        val xTarget = 20..30
//        val yTarget = -10..-5
        val xTarget = 88..125
        val yTarget = -157..-103

        var xVRangeMin = Int.MAX_VALUE
        var xVRangeMax = Int.MIN_VALUE
        val velocities = buildVelocities(xTarget, xVRangeMin, xVRangeMax, yTarget)

        return velocities.maxByOrNull { it.value }?.value ?: -1
    }

    private fun buildVelocities(
        xTarget: IntRange,
        xVRangeMin: Int,
        xVRangeMax: Int,
        yTarget: IntRange
    ): MutableMap<Pair<Int, Int>, Int> {
        //build x options
        var xVRangeMin = xVRangeMin
        var xVRangeMax = xVRangeMax
        var n = 1
        while (n <= xTarget.last) {
            val v = n * (n + 1) / 2
            if (v in xTarget) {
                if (xVRangeMin > n) xVRangeMin = n
                if (xVRangeMax < n) xVRangeMax = n
                logger.debug("$n -> $v")
            }
            n++
        }

        val velocities = mutableMapOf<Pair<Int, Int>, Int>()
        (xVRangeMin..xTarget.last).forEach { vX ->
            (yTarget.first..yTarget.first.absoluteValue).forEach { vY ->
                val p = Probe(vX, vY, xTarget, yTarget)
                var i = 1
                while (p.doStep().not()) {
                    i++
                    if (i > 1000) return@forEach
                }

                velocities[vX to vY] = p.highestY
            }
        }
        return velocities
    }

    override fun doPart2(input: List<String>): Any {
//        val xTarget = 20..30
//        val yTarget = -10..-5
        val xTarget = 88..125
        val yTarget = -157..-103

        var xVRangeMin = Int.MAX_VALUE
        var xVRangeMax = Int.MIN_VALUE
        val velocities = buildVelocities(xTarget, xVRangeMin, xVRangeMax, yTarget)

        return velocities.count()
    }
}

class Probe(var vX: Int, var vY: Int, val xTarget: IntRange, val yTarget: IntRange) {
    val logger = LoggerFactory.getLogger(this.javaClass)
    var x: Int = 0
    var y: Int = 0

    var highestY = 0


    fun doStep(): Boolean {
        x += vX
        y += vY
        vX = when {
            vX > 0 -> vX - 1
            vX < 0 -> vX + 1
            else -> vX
        }
        vY--

        if (y > highestY) highestY = y
        return x in xTarget && y in yTarget
    }
}