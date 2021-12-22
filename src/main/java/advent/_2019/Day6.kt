package advent._2019

import advent.util.ADay
import advent.util.Day

data class Planet(val key: String) {
    var orbits: Planet? = null
    val orbiters = mutableSetOf<Planet>()
}

class Map {
    val planets = mutableMapOf<String, Planet>()

    fun addOrbiter(center: String, orbiter: String) {
        val orbiterPlanet = getPlanet(orbiter)
        val centerPlanet = getPlanet(center)
        centerPlanet.orbiters.add(orbiterPlanet)
        orbiterPlanet.orbits = centerPlanet
    }

    fun findShortestPath(from: String, to: String): Int {
        val fromPlanet = getPlanet(from)
        val toPlanet = getPlanet(to)

        return findOrbiter(fromPlanet.orbits!!, toPlanet, fromPlanet, 0)
    }

    fun findOrbiter(fromPlanet: Planet, toPlanet: Planet, previousPlanet: Planet?, pathLength: Int): Int {
        if (fromPlanet.orbiters.contains(toPlanet)) return pathLength

        val pathOptions = mutableListOf<Planet>()
        fromPlanet.orbits?.let {
            if (it != previousPlanet) {
                pathOptions.add(it)
            }
        }
        pathOptions.addAll(fromPlanet.orbiters.filter { it != previousPlanet })

        return pathOptions.map { findOrbiter(it, toPlanet, fromPlanet, pathLength + 1) }.minOrNull() ?: Int.MAX_VALUE
    }

    fun countPathsOutwardFrom(planet: String): Int {
        return getPlanet(planet).orbiters.map { countPathsOutward(it, 0) }.sum()
    }

    private fun countPathsOutward(planet: Planet, pathLength: Int): Int {
        return pathLength + 1 + planet.orbiters.map { countPathsOutward(it, pathLength + 1) }.sum()
    }

    private fun getPlanet(center: String) = planets[center] ?: Planet(center).also { planets[center] = it }

}

@ADay(2019, 6, "day6")
class Day6 : Day() {

    private val CENTER = "COM"

    override fun doPart1(input: List<String>): Any {
        val map = createMap(input)

        return map.countPathsOutwardFrom(CENTER)
    }

    override fun doPart2(input: List<String>): Any {
        val map = createMap(input)

        return map.findShortestPath("YOU", "SAN")
    }

    private fun createMap(input: List<String>): Map {
        val map = Map()
        input.forEach {
            val line = it.split(')')
            map.addOrbiter(line[0], line[1])
        }
        return map
    }

}