package advent._2021

import advent.util.*
import java.lang.Integer.max

@ADay(2021, 19, "day19")
class Day19 : Day {
    val orientations = 24

    override fun doPart1(input: List<String>): Any {
        val scannerList = buildScanners(input)

        val grid = Grid3D('.')
        grid[Point3D(0, 0, 0)] = 'S'
        scannerList.first().forEach { grid[it] = 'B' }

        val scannersExpanded = expandAll(scannerList)

        val needMatches = 6

        while (scannersExpanded.isNotEmpty()) {
            scannersExpanded.toSet().forEach { scannerOrientations ->
                val s = findMatchingOrientation(scannerOrientations, grid, needMatches)
                logger.debug("matches? -> ${s != null}")
                if (s != null) {
                    scannersExpanded.removeAt(scannersExpanded.indexOfFirst { it === s })
                }
            }
        }

        return grid.values.count { it == 'B' }
    }

    override fun doPart2(input: List<String>): Any {
        val scannerList = buildScanners(input)

        val grid = Grid3D('.')
        grid[Point3D(0, 0, 0)] = 'S'
        scannerList.first().forEach { grid[it] = 'B' }

        val scannersExpanded = expandAll(scannerList)

        val needMatches = 6

        while (scannersExpanded.isNotEmpty()) {
            scannersExpanded.toSet().forEach { scannerOrientations ->
                val s = findMatchingOrientation(scannerOrientations, grid, needMatches)
                logger.debug("matches? -> ${s != null}")
                if (s != null) {
                    scannersExpanded.removeAt(scannersExpanded.indexOfFirst { it === s })
                }
            }
        }

        val scannerKeys = grid.entries.filter { it.value == 'S' }.map { it.key }
        var highestDistance = 0
        scannerKeys.forEach { a ->
            scannerKeys.forEach { b ->
                if (a == b) return@forEach;
                highestDistance = max(highestDistance, a.distanceManhattan(b))
            }
        }

        return highestDistance
    }

    private fun expandAll(scannerList: MutableList<List<Point3D>>): MutableList<List<MutableList<Point3D>>> {
        val scannersExpanded = scannerList.drop(1).map {
            val lists = Array(orientations) { mutableListOf<Point3D>() }
            it.map { it.all() }.forEach { subList ->
                repeat(orientations) { i ->
                    lists[i] += subList[i]
                }
            }
            lists.toList()
        }.toMutableList()
        return scannersExpanded
    }

    private fun buildScanners(input: List<String>): MutableList<List<Point3D>> {
        val scannerList = mutableListOf<List<Point3D>>()
        var newList = mutableListOf<Point3D>()
        input.forEach {
            if (it.startsWith("--")) {
                newList = mutableListOf()
                scannerList += newList
                return@forEach
            }
            if (it.isBlank()) return@forEach
            newList += it.to3DPoint()
        }
        return scannerList
    }

    private fun findMatchingOrientation(
        scannerOrientations: List<MutableList<Point3D>>,
        grid: Grid3D<Char>,
        needMatches: Int,
    ): List<MutableList<Point3D>>? {
        scannerOrientations.forEach { scanner ->
            grid.keys.forEach { gridPoint ->
                for (p in scanner) {
                    val diff = gridPoint - p
                    val translated = scanner.map { it + diff }
                    val count = translated.filter { grid[it] != null }.count()
//                    logger.debug("$count -> $translated")
                    if (count >= needMatches) {
                        grid[diff] = 'S'
                        translated.forEach { grid[it] = 'B' }
                        return scannerOrientations
                    }
                }
            }
        }
        return null
    }
}