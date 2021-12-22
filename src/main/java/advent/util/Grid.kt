package advent.util

import org.slf4j.LoggerFactory
import java.lang.Integer.min
import java.util.*
import kotlin.math.max

class Grid<T>(var default: T) : HashMap<Point, T>() {

    val logger = LoggerFactory.getLogger(this.javaClass)

    companion object {
        val right = Point(1, 0)
        val left = Point(-1, 0)
        val up = Point(0, -1)
        val down = Point(0, 1)
    }

    var highestX = 0
    var lowestX = 0
    var highestY = 0
    var lowestY = 0

    fun foldUp(foldY: Int, combine: (a: T?, b: T?) -> T?) {
        (0 until foldY).forEach { y ->
            (0..highestX).forEach { x ->
                val pointA = Point(x, y)
                val result = combine(get(pointA), get(x, ((foldY * 2) - y)))
                when (result) {
                    null -> remove(pointA)
                    else -> this[pointA] = result
                }

            }
        }
        this.keys.filter { it.y >= foldY }.forEach { remove(it) }
        highestY = foldY - 1
    }

    fun foldLeft(foldX: Int, combine: (a: T?, b: T?) -> T?) {
        (0..highestY).forEach { y ->
            (0 until foldX).forEach { x ->
                val pointA = Point(x, y)
                val result = combine(get(pointA), get(((foldX * 2) - x), y))
                when (result) {
                    null -> remove(pointA)
                    else -> this[pointA] = result
                }
            }
        }
        this.keys.filter { it.x >= foldX }.forEach { remove(it) }
        highestX = foldX - 1
    }

    fun findPath(start: Point, end: Point, directions: List<Point>, mapToLong: (T) -> Int): Int? {
        val visited = mutableSetOf<Point>()

        // point -> cost to get there
        val todo = PriorityQueue<Pair<Point, Int>> { a, b -> a.second - b.second }

        todo.add(start to 0)
        while (todo.isNotEmpty()) {
            val (start, cost) = todo.poll()

            if (start == end) {
                return cost
            }

            if (start in visited) continue
            visited += start

            directions
                .map { it + start }
                .filter { it in this }
                .filter { it !in visited }
                .forEach { todo.add(it to (cost + mapToLong(getOrDefault(it)))) }

        }
        return -1
    }


    fun get(x: Int, y: Int) = get(Point(x, y))
    fun getOrDefault(x: Int, y: Int) = getOrDefault(Point(x, y))
    fun getOrDefault(key: Point): T = getOrDefault(key, default)

    fun max() = Point(highestX, highestY)

    override fun put(key: Point, value: T): T? {
        highestX = max(highestX, key.x)
        lowestX = min(lowestX, key.x)
        highestY = max(highestY, key.y)
        lowestY = min(lowestY, key.x)
        return super.put(key, value)
    }

    fun contains(p: Point) = p.x in 0..highestX && p.y in 0..highestY

    override fun toString(): String {
        var output = "\n"
        (lowestY..highestY).forEach { y ->
            output += (lowestX..highestX).map { x -> getOrDefault(x, y) }.joinToString("") + "\n"
        }
        return output
    }
}

data class Point(val x: Int, val y: Int) {
    operator fun plus(point: Point) = Point(this.x + point.x, this.y + point.y)
    operator fun minus(point: Point) = Point(this.x - point.x, this.y - point.y)
    operator fun times(point: Point) = Point(this.x * point.x, this.y * point.y)
    operator fun div(point: Point) = Point(this.x / point.x, this.y / point.y)
}

fun String.toPoint(seperator: String = ",") = this.split(seperator).let { Point(it[0].toInt(), it[1].toInt()) }