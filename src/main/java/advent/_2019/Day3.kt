package advent._2019

import advent.util.ADay
import advent.util.Day
import advent._2019.models.LinePath
import kotlin.math.absoluteValue
import kotlin.math.min

data class Point(val x: Int, val y: Int) {
    val distance = (x.absoluteValue + y.absoluteValue)
}

enum class Direction { LEFT, RIGHT }
data class Section(
    val from: Int,
    val to: Int,
    val level: Int,
    val stepsTakenToGetHere: Int,
    val direction: Direction = Direction.RIGHT
)
@ADay(2019, 3, "day3")
class Day3 : Day {

    override fun doPart1(input: List<String>): Any {
        val paths = mapInput(input)
        val path1 = paths[0]
        val path2 = paths[1]

        val point1 = findClosestCrossPoint(path1, path2)
        val point2 = findClosestCrossPoint(path2, path1)
        return min(point1!!.distance, point2!!.distance)
    }

    override fun doPart2(input: List<String>): Any {
        val paths = mapInput(input)
        val path1 = paths[0]
        val path2 = paths[1]

        return min(
            findClosestSteps(path1, path2),
            findClosestSteps(path2, path1)
        )
    }

    private fun mapInput(input: List<String>): List<LinePath> {
        return input.map { line ->
            LinePath()
                .also {
                    line.split(',')
                        .forEach() { str -> it.addStep(str[0], str.substring(1).toInt()) }
                }
        }
    }

    private fun findClosestCrossPoint(path1: LinePath, path2: LinePath): Point? {
        var closestPoint: Point? = null
        doPerCrossPoint(path1, path2) { _, _, cross ->
            if (closestPoint == null || cross.distance < closestPoint!!.distance) {
                closestPoint = cross
            }
        }
        return closestPoint
    }

    private fun findClosestSteps(path1: LinePath, path2: LinePath): Int {
        var totalSteps: Int = Int.MAX_VALUE
        doPerCrossPoint(path1, path2) { horizontal, vertical, _ ->
            val dist = horizontal.stepsTakenToGetHere + lastMileSteps(
                horizontal,
                vertical
            ) +
                    vertical.stepsTakenToGetHere + lastMileSteps(
                vertical,
                horizontal
            )

            if (dist < totalSteps) {
                totalSteps = dist
            }
        }
        return totalSteps
    }

    private fun lastMileSteps(horizontal: Section, vertical: Section): Int {
        return when (horizontal.direction) {
            Direction.RIGHT -> vertical.level - horizontal.from
            Direction.LEFT -> horizontal.to - vertical.level
        }
    }

    private fun doPerCrossPoint(
        path1: LinePath,
        path2: LinePath,
        block: (horizontal: Section, vertical: Section, cross: Point) -> Unit
    ) {
        path1.horizontalSections.forEach { horizontal ->
            path2.verticalSections.forEach { vertical ->
                if (horizontal.from <= vertical.level
                    && vertical.level <= horizontal.to
                    && vertical.from <= horizontal.level
                    && horizontal.level <= vertical.to
                ) {
                    val cross = Point(horizontal.level, vertical.level)
                    if (cross.distance > 0) {
                        block(horizontal, vertical, cross)
                    }
                }
            }
        }
    }
}