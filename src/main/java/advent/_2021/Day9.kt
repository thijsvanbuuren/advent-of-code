package advent._2021

import advent.util.ADay
import advent.util.Day

@ADay(2021, 9, "day9")
class Day9 : Day {
    override fun doPart1(input: List<String>): Any {
        // y, x
        val data = input.map { it.map { Character.getNumericValue(it) } }
        val rows = input.size
        val columns = input.first().length

        var total = 0
        (0 until columns).forEach { x ->
            (0 until rows).forEach { y ->
                val compares = buildAroundHeights(data, y, x).values
                val height = data[y][x]
                if (compares.minOf { it } > height) {
                    total += height + 1
                }
            }
        }
        return total
    }

    override fun doPart2(input: List<String>): Any {
        // y, x
        val data = input.map { it.map { Character.getNumericValue(it) } }
        val rows = input.size
        val columns = input.first().length

        var total = 0
        val basinSizes = mutableListOf<Int>()
        (0 until columns).forEach { x ->
            (0 until rows).forEach { y ->
                var basin = 1
                val height = data[y][x]

                basin += findBasinSize(data, y, x, height)

                if (basin > 1)
                    basinSizes += basin
            }
        }
        basinSizes.sort()

        return basinSizes.subList(basinSizes.size - 3, basinSizes.size).reduce { a, b -> a * b }
    }

    private fun findBasinSize(
        data: List<List<Int>>,
        y: Int,
        x: Int,
        height: Int,
    ): Int {
        val ignoreList = mutableListOf<Pair<Int, Int>>()
        buildValidAroundHeights(data, y, x, height, ignoreList)
        val result = buildValidAroundHeights(data, y, x, height, ignoreList)

        val results = result.toMap()
        return results.count()
    }

    private fun buildValidAroundHeights(
        data: List<List<Int>>,
        y: Int,
        x: Int,
        height: Int,
        ignore: MutableList<Pair<Int, Int>> = mutableListOf()
    ): List<Pair<Pair<Int, Int>, Int>> {
        ignore += y to x
        val compares = buildAroundHeights(data, y, x)
        val filtered = compares.filter { (key, _) -> ignore.contains(key).not() }
        val count = filtered.values.count { it > height }
        return if (count == filtered.size) {
            compares
                .filter { (_, value) -> value > height  }
                .filter { (_, value) -> value != 9 }
                .map { it.toPair() }
                .onEach { ignore += it.first.first to it.first.second }
                .flatMap { entry ->
                    buildValidAroundHeights(
                        data,
                        entry.first.first,
                        entry.first.second,
                        entry.second,
                        ignore
                    ) + entry
                }
        } else emptyList()
    }

    private fun buildAroundHeights(
        data: List<List<Int>>,
        y: Int,
        x: Int
    ): Map<Pair<Int, Int>, Int> {
        return listOf(
            y - 1 to x,
            y to x - 1,
            y + 1 to x,
            y to x + 1
        )
            .mapNotNull { pair -> data.getOrNull(pair.first)?.getOrNull(pair.second)?.let { pair to it } }.toMap()
    }
}