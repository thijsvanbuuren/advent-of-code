package advent._2021

import advent.util.ADay
import advent.util.Day
import advent.util.Grid
import advent.util.toPoint
import org.slf4j.LoggerFactory

@ADay(2021, 13, "day13")
class Day13 : Day {

    val logger = LoggerFactory.getLogger("Day 13")

    val data = "\u2588"

    override fun doPart1(input: List<String>): Any {
        val folds = mutableListOf<String>()
        val grid = Grid(".")
        parseData(input, folds, grid)

        folds.first().let {
            doCommand(it, grid)
        }

//        logger.debug(grid.toString())
        return grid.size
    }

    private fun doCommand(it: String, grid: Grid<String>) {
        val splitted = it.split("=")
        val command = splitted[0]
        val amount = splitted[1].toInt()
        when (command.trim()) {
            "fold along x" -> grid.foldLeft(amount, combine)
            "fold along y" -> grid.foldUp(amount, combine)
        }
    }

    override fun doPart2(input: List<String>): Any {
        val folds = mutableListOf<String>()
        val grid = Grid(" ")
        parseData(input, folds, grid)

        folds.forEach {
            doCommand(it, grid)
        }

        logger.debug(grid.toString())
        return grid.size
    }

    val combine: (a: String?, b: String?) -> String? = { a, b ->
        if (a == data || b == data) data else null
    }

    private fun parseData(
        input: List<String>,
        folds: MutableList<String>,
        grid: Grid<String>
    ) {
        var foldMode = false
        input.forEach {
            when {
                it.isEmpty() -> foldMode = true
                foldMode.not() -> grid[it.toPoint()] = data
                foldMode -> folds += it
            }
        }
    }
}