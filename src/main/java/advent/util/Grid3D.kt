package advent.util

import org.slf4j.LoggerFactory
import kotlin.math.absoluteValue
import kotlin.math.max

class Grid3D<T>(private val default: T) : HashMap<Point3D, T>() {

    val logger = LoggerFactory.getLogger(this.javaClass)

    companion object {
        val right = Point3D(1, 0, 0)
        val left = Point3D(-1, 0, 0)
        val up = Point3D(0, 0, 1)
        val down = Point3D(0, 0, -1)
        val forwad = Point3D(0, 1, 0)
        val backward = Point3D(0, -1, 0)
    }

    private var highestX = 0
    private var highestY = 0
    private var highestZ = 0

    fun get(x: Int, y: Int, z: Int) = get(Point3D(x, y, z))
    fun getOrDefault(x: Int, y: Int, z: Int) = getOrDefault(Point3D(x, y, z))
    fun getOrDefault(key: Point3D): T = getOrDefault(key, default)

    fun max() = Point3D(highestX, highestY, highestZ)

    override fun put(key: Point3D, value: T): T? {
        highestX = max(highestX, key.x)
        highestY = max(highestY, key.y)
        return super.put(key, value)
    }

    fun contains(p: Point3D) = p.x in 0..highestX && p.y in 0..highestY

    override fun toString(): String {
        var output = "\n"
        entries.forEach {
            output += "${it.key} -> ${it.value}"
        }
        return output
    }
}

/**
 * x = left/right
 * y = forward/backward
 * z = up/down
 */
data class Point3D(val x: Int, val y: Int, val z: Int) {
    companion object {
        val NEGATOR = Point3D(-1, -1, -1)
    }

    operator fun plus(point: Point3D) = Point3D(this.x + point.x, this.y + point.y, this.z + point.z)
    operator fun minus(point: Point3D) = Point3D(this.x - point.x, this.y - point.y, this.z - point.z)
    operator fun times(point: Point3D) = Point3D(this.x * point.x, this.y * point.y, this.z * point.z)
    operator fun div(point: Point3D) = Point3D(this.x / point.x, this.y / point.y, this.z / point.z)

    fun turnRight() = Point3D(this.y, this.x * -1, this.z)
    fun turnLeft() = Point3D(this.y * -1, this.x, this.z)

    fun turnUp() = Point3D(this.x, this.z, this.y * -1)
    fun turnDown() = Point3D(this.x, this.z * -1, this.y)
    fun negate() = this * NEGATOR

    fun all() = allDirections().flatMap { it.allRotations() }

    fun allDirections() = listOf(
        this,
        this.turnUp(),
        this.turnDown(),
        this.turnRight(),
        this.turnRight().turnRight(),
        this.turnLeft(),
    )

    fun allRotations() = mutableListOf(this, this.rotateYRight()).also {
        it += it.last().rotateYRight()
        it += it.last().rotateYRight()
    }


    fun rotateYRight() = Point3D(this.z, this.y, this.x * -1)

    fun distanceManhattan(toPoint: Point3D): Int {
        return (this.x - toPoint.x).absoluteValue +
                (this.y - toPoint.y).absoluteValue +
                (this.z - toPoint.z).absoluteValue
    }

    override fun toString(): String {
        return "\n$x,$y,$z"
    }
}

fun String.to3DPoint(seperator: String = ",") =
    this.split(seperator).let { Point3D(it[0].toInt(), it[1].toInt(), it[2].toInt()) }