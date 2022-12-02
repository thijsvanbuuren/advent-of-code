package advent.util.dimensional

class Cube(val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {

    fun subtract(cube: Cube) {
        val xOverlap: IntRange = overlapRange(xRange, cube.xRange) ?: return
        val yOverlap: IntRange = overlapRange(yRange, cube.yRange) ?: return
        val zOverlap: IntRange = overlapRange(zRange, cube.zRange) ?: return
    }

    private fun overlapRange(a: IntRange, b: IntRange): IntRange? =
        if (a.first < b.first && a.last > b.last) {
            b.first..b.last
        } else if (a.first in b && a.last in b) {
            b.first..a.last
        } else if (a.first in b) {
            a.first..b.last
        } else if (a.last in b) {
            b.first..a.last
        } else null
}