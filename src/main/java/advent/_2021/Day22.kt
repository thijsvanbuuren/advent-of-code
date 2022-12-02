package advent._2021

import advent.util.ADay
import advent.util.Day
import advent.util.Grid3D

@ADay(2021, 22, "day22")
class Day22 : Day(true) {
    override fun doPart1(input: List<String>): Any {
        val engine = Grid3D(false)
        input.forEach {
            val (mode, ranges) = it.split(" ").let { (it[0] == "on") to it[1] }
            val (xRange, yRange, zRange) = ranges.split(",")
                .map {
                    it.split("..")
                        .let { IntRange(it[0].substring(2).toInt(), it[1].toInt()) }
                }
                .let { Triple(it[0], it[1], it[2]) }


//            xRange.filter { it in -50..50 }.forEach { x ->
//                yRange.filter { it in -50..50 }.forEach { y ->
//                    zRange.filter { it in -50..50 }.forEach { z ->
//                        engine[Point3D(x, y, z)] = mode
//                    }
//                }
//            }
        }
        return engine.count { it.value }
    }


    // TODO possible idea, split cubes in to smaller cubes when there is overlap
    override fun doPart2(input: List<String>): Any {
        val engine = Grid3D(false)
        var (mode, firstRanges) = parse(input.first())
        var count = 0L
        var lastSize = calcSize(firstRanges)
        if (mode) count += lastSize

        var countMap = mutableMapOf(firstRanges to lastSize)

        log("$mode, o: -, c:$count, $firstRanges")
        input.drop(1).forEach {
            val (mode, ranges) = parse(it)

            countMap.keys.forEach { prevRanges ->
                val overlap: Long = checkOverlap(prevRanges.first, ranges.first).toLong() *
                        checkOverlap(prevRanges.second, ranges.second) *
                        checkOverlap(prevRanges.third, ranges.third)

                countMap[prevRanges] = countMap[prevRanges]!! - overlap
            }

            if (mode) {
                countMap[ranges] = calcSize(ranges)
            }

            firstRanges = ranges
//            log("$mode, o:$overlap, c:$count, $ranges")
//            xRange.first in xRange
        }
        return countMap.entries.sumOf { it.value }
    }

    private fun calcSize(ranges: Triple<IntRange, IntRange, IntRange>): Long {
        val (xRange, yRange, zRange) = ranges

        val xLength = xRange.last - xRange.first
        val yLength = yRange.last - yRange.first
        val zLength = zRange.last - zRange.first

        return xLength.toLong() * yLength * zLength
    }

    private fun checkOverlap(a: IntRange, b: IntRange) =
        if (a.first < b.first && a.last > b.last) {
            b.last - b.first
        } else if (a.first in b && a.last in b) {
            a.last - b.first
        } else if (a.first in b) {
            b.last - a.first
        } else if (a.last in b) {
            a.last - b.first
        } else 0

    private fun parse(it: String): Pair<Boolean, Triple<IntRange, IntRange, IntRange>> {
        val (mode, ranges) = it.split(" ").let { (it[0] == "on") to it[1] }
        val current = ranges.split(",")
            .map {
                it.split("..")
                    .let { IntRange(it[0].substring(2).toInt(), it[1].toInt()) }
            }
            .let { Triple(it[0], it[1], it[2]) }
        return mode to current
    }
}