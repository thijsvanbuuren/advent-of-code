package advent._2021

import advent.util.ADay
import advent.util.Day
import org.slf4j.LoggerFactory

@ADay(2021, 11, "day11")
class Day11 : Day() {

    override fun doPart1(input: List<String>): Any {
        val data = input.map { it.map { Character.getNumericValue(it) }.toMutableList() }.toMutableList()

        return (0 until 100).sumOf { sim(data) }
    }

    override fun doPart2(input: List<String>): Any {
        val data = input.map { it.map { Character.getNumericValue(it) }.toMutableList() }.toMutableList()

        var step = 1
        while (sim(data) != 100) {
            step++
        }
        return step
    }

    private fun simRecursive(data: MutableList<MutableList<Int>>): Int {
        //step 1
        val rows = data.size
        val columns = data.first().size

        val flashed = mutableSetOf<Pair<Int, Int>>()
        (0 until columns).forEach { x ->
            (0 until rows).forEach { y ->
                doFlash(data, y, x, flashed)
            }
        }
        return flashed.count()
    }

    private fun doFlash(
        data: MutableList<MutableList<Int>>,
        y: Int,
        x: Int,
        flashed: MutableSet<Pair<Int, Int>>,
    ) {
        var level = data.getOrNull(y)?.getOrNull(x) ?: return
        if (flashed.contains(y to x)) return

        level++
        data[y][x] = level
        if (level > 9) {
            flashed += y to x
            data[y][x] = 0
            doFlash(data, y + 1, x - 1, flashed)
            doFlash(data, y + 1, x, flashed)
            doFlash(data, y + 1, x + 1, flashed)
            doFlash(data, y, x - 1, flashed)
            doFlash(data, y, x + 1, flashed)
            doFlash(data, y - 1, x - 1, flashed)
            doFlash(data, y - 1, x, flashed)
            doFlash(data, y - 1, x + 1, flashed)
        }
    }


    private fun sim(data: MutableList<MutableList<Int>>): Int {
        //step 1
        val rows = data.size
        val columns = data.first().size

        val flashed = mutableSetOf<Pair<Int, Int>>()
        val todo = mutableSetOf<Pair<Int, Int>>()
        (0 until columns).forEach { x ->
            (0 until rows).forEach { y ->
                addOne(data, y, x, todo)
            }
        }
//        logger.debug(" TODO: ${todo.size}")

        while (todo.isNotEmpty()) {
            val it = todo.first()
            todo -= it
            if (flashed.contains(it)) continue
            flashed += it

            val y = it.first
            val x = it.second
            addOne(data, y + 1, x - 1, todo)
            addOne(data, y + 1, x, todo)
            addOne(data, y + 1, x + 1, todo)
            addOne(data, y, x - 1, todo)
            addOne(data, y, x + 1, todo)
            addOne(data, y - 1, x - 1, todo)
            addOne(data, y - 1, x, todo)
            addOne(data, y - 1, x + 1, todo)
        }

        flashed.forEach { data[it.first][it.second] = 0 }
//        data.forEach { logger.debug("${it}") }
        return flashed.count()
    }

    private fun addOne(
        data: MutableList<MutableList<Int>>,
        y: Int,
        x: Int,
        todo: MutableSet<Pair<Int, Int>>
    ) {
        var level = data.getOrNull(y)?.getOrNull(x) ?: return
        level++
        data[y][x] = level
        if (level > 9) {
            todo += y to x
        }
    }
}