package advent._2019

import advent.util.ADay
import advent.util.Day

data class Asteroid(val x: Int, val y: Int)

@ADay(2019, 10, "day10")
class Day10 : Day {


    override fun doPart1(data: List<String>): Any {
        val asteroids = readAsteroids(data)
        asteroids.map { }
        return asteroids
    }


    override fun doPart2(data: List<String>): Any {
        return -1
    }


    private fun readAsteroids(data: List<String>): MutableList<Asteroid> {
        val asteroids = mutableListOf<Asteroid>()
        var x = 0
        var y = 0
        data.forEach {
            it.forEach {
                if (it == '#') {
                    asteroids.add(Asteroid(x, y))
                }
                x++
            }
            x = 0
            y++
        }
        return asteroids
    }
}