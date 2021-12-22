package advent._2021

import advent.util.ADay
import advent.util.Day
import org.slf4j.LoggerFactory

data class Cave(val key: String, val maxCount: Int = 1) {
    val links = mutableListOf<Cave>()
    fun add(to: Cave) {
        links += to
    }

    val isBig = key.first().isUpperCase()

    fun findPaths(paths: List<MutableList<Cave>>, maxed: Boolean): List<List<Cave>> {
        if (links.isEmpty()) return emptyList()

        val result = mutableListOf<List<Cave>>()

        (0 until paths.size).map { paths[it] }.forEach { cavePath ->
            (0 until links.size).forEach { index ->
                val toCave = links[index]

                val newCavePath = ArrayList(cavePath)
                result += newCavePath
                val addedToMaxed = addCaveToPath(toCave, newCavePath, maxed)
                if (addedToMaxed.first) {
                    if (toCave.key != "end") {
                        result += toCave.findPaths(listOf(newCavePath), addedToMaxed.second)
                    }
                }
            }
        }
        return result.filter { it.last().key == "end" }
    }

    private fun addCaveToPath(
        toCave: Cave,
        cavePath: MutableList<Cave>,
        maxed: Boolean
    ): Pair<Boolean, Boolean> {
        val caveCount = if (toCave.isBig.not()) cavePath.count { it == toCave } else -1
        val newMaxed = if (maxed || toCave.isBig) maxed else (maxCount > 1 && (caveCount + 1) >= maxCount)
        if (toCave.isBig || caveCount == 0 || (caveCount < maxCount && maxed.not())) {
            cavePath += toCave
            return true to newMaxed
        }
        return false to newMaxed
    }

    override fun toString() = key
}

@ADay(2021, 12, "day12")
class Day12 : Day() {

    override fun doPart1(input: List<String>): Any {
        val caves = buildCaves(input, 1)
        val start = caves["start"] ?: return -1

        val paths = start.findPaths(mutableListOf(mutableListOf(start)), false)

//        paths.forEach {
//            logger.debug("$it")
//        }
        return paths.size
    }


    override fun doPart2(input: List<String>): Any {
        val caves = buildCaves(input, 2)
        val start = caves["start"] ?: return -1

        val paths = start.findPaths(mutableListOf(mutableListOf(start)), false)

//        paths.forEach {
//            logger.debug("$it")
//        }
        return paths.size
    }

    private fun buildCaves(input: List<String>, maxCount: Int): MutableMap<String, Cave> {
        val caves = mutableMapOf<String, Cave>()
        input.forEach {
            val splitted = it.split("-")
            val from = caves.getOrPut(splitted[0]) { Cave(splitted[0], maxCount) }
            val to = caves.getOrPut(splitted[1]) { Cave(splitted[1], maxCount) }
            if (to.key != "start" && from.key != "end") {
                from.add(to)
            }
            if (from.key != "start" && to.key != "end") {
                to.add(from)
            }
        }
        return caves
    }
}