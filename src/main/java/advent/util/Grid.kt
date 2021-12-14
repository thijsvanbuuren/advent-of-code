package advent.util

import kotlin.math.max

class Grid<T>(private val default: T) : HashMap<Point, T>() {

    private var highestX = 0
    private var highestY = 0

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


    fun get(x: Int, y: Int) = get(Point(x, y))
    fun getOrDefault(x: Int, y: Int) = getOrDefault(Point(x, y))
    fun getOrDefault(key: Point): T = getOrDefault(key, default)

    override fun put(key: Point, value: T): T? {
        highestX = max(highestX, key.x)
        highestY = max(highestY, key.y)
        return super.put(key, value)
    }


    override fun toString(): String {
        var output = "\n"
        (0..highestY).forEach { y ->
            output += (0..highestX).map { x -> getOrDefault(x, y) }.joinToString("") + "\n"
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