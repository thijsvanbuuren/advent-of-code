package advent._2021

import advent.util.ADay
import advent.util.Day
import advent.util.Grid
import advent.util.Point

@ADay(2021, 20, "day20")
class Day20 : Day() {
    override fun doPart1(input: List<String>): Any {
        val algorithm = input.first()

        var grid = Grid('.')
        input.drop(2).forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                grid[Point(x, y)] = char
            }
        }

//        logger.debug(grid.toString())
        repeat(2) {
            grid = enhance(grid, algorithm, 2)
//            logger.debug(grid.toString())
        }

        return grid.count { it.value == '#' }
    }

    override fun doPart2(input: List<String>): Any {
        val algorithm = input.first()

        var grid = Grid('.')
        input.drop(2).forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                grid[Point(x, y)] = char
            }
        }

//        logger.debug(grid.toString())
        repeat(50) {
            grid = enhance(grid, algorithm, 2)
//            logger.debug(grid.toString())
        }

        return grid.count { it.value == '#' }
    }

    private fun enhance(
        grid: Grid<Char>,
        algorithm: String,
        padding: Int
    ): Grid<Char> {
        var grid1 = grid
        val bigGrid = Grid(
            if (grid1.default == '.') {
                algorithm["000000000".toInt(2)]
            } else {
                algorithm["111111111".toInt(2)]
            }
        )

        ((grid1.lowestX - padding)..(grid1.highestX + padding)).forEach { x ->
            ((grid1.lowestY - padding)..(grid1.highestY + padding)).forEach { y ->
                val index = findAlgIndex(y, x, grid1)
                bigGrid[Point(x, y)] = algorithm[index]
            }
        }

        grid1 = bigGrid
        return grid1
    }

    private fun findAlgIndex(y: Int, x: Int, grid: Grid<Char>): Int {
        var strNr = ""
        (y - 1..y + 1).forEach { subY ->
            (x - 1..x + 1).forEach { subX ->
                strNr += when (grid.getOrDefault(subX, subY)) {
                    '#' -> '1'
                    else -> '0'
                }
            }
        }
        val index = strNr.toInt(2)
        return index
    }
}