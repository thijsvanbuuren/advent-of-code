package advent._2019.models

import advent._2019.Direction
import advent._2019.Point
import advent._2019.Section

data class LinePath(
    val verticalSections: MutableList<Section> = mutableListOf(),
    val horizontalSections: MutableList<Section> = mutableListOf()
) {
    private var current = Point(0, 0)
    private var stepsTaken = 0
    private val pathList = mutableListOf(current)

    fun addStep(key: Char, stepSize: Int) {
        current = when (key) {
            'R' -> goRight(stepSize)
            'L' -> goLeft(stepSize)
            'U' -> goUp(stepSize)
            'D' -> goDown(stepSize)
            else -> throw IllegalArgumentException("Something went wrong parsing $key")
        }
        pathList.add(current)
        stepsTaken += stepSize
    }

    private fun goRight(stepSize: Int) = Point(current.x + stepSize, current.y)
        .also { newPoint ->
            Section(current.x, newPoint.x, newPoint.y, stepsTaken)
                .also { horizontalSections.add(it) }
        }

    private fun goLeft(stepSize: Int) = Point(current.x - stepSize, current.y)
        .also { newPoint ->
            Section(
                newPoint.x,
                current.x,
                newPoint.y,
                stepsTaken,
                Direction.LEFT
            )
                .also { horizontalSections.add(it) }
        }

    private fun goUp(stepSize: Int) = Point(current.x, current.y + stepSize)
        .also { newPoint ->
            Section(current.y, newPoint.y, newPoint.x, stepsTaken)
                .also { verticalSections.add(it) }
        }

    private fun goDown(stepSize: Int) = Point(current.x, current.y - stepSize)
        .also { newPoint ->
            Section(
                newPoint.y,
                current.y,
                newPoint.x,
                stepsTaken,
                Direction.LEFT
            )
                .also { verticalSections.add(it) }
        }
}