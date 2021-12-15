package advent._2021

import advent.util.ADay
import advent.util.Day
import advent.util.Grid
import advent.util.Point

@ADay(2021, 15, "day15")
class Day15 : Day {
    override fun doPart1(input: List<String>): Any {
        val grid = Grid(0)

        (0 until input.size).forEach { y ->
            val line = input[y]
            (0 until line.length).forEach { x ->
                grid[Point(x, y)] = Character.getNumericValue(line[x])
            }
        }

        return grid.findPath(Point(0, 0), grid.max(), listOf(Grid.down, Grid.right)) { it } ?: -1
//            .also { logger.debug(grid.toString()) } ?: -1
    }

    override fun doPart2(input: List<String>): Any {
        val grid = Grid(0)

        val width = input.first().length
        val height = input.size

        val enlargment = 5
        (0 until (height * enlargment)).forEach { y ->
            val rY = y % height
            val line = input[rY]
            (0 until (width * enlargment)).forEach { x ->
                val rX = x % width
                val initialV = Character.getNumericValue(line[rX])

                val timesX = x / width
                val timesY = y / height
                grid[Point(x, y)] = determineNext(initialV, timesX + timesY)
            }
        }
        // 85745282229685639333179674144428178525553928963666
        // 85745282229685639333179674144428178525553928963666
//        logger.debug(grid.toString())
        return grid.findPath(Point(0, 0), grid.max(), listOf(Grid.down, Grid.right, Grid.up, Grid.left)) { it } ?: -1
    }

    private fun determineNext(initialV: Int, times: Int): Int {
        val newV = initialV + times
        return if (newV > 9) newV % 10 + 1 else newV
    }
}