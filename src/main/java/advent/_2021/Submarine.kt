package advent._2021

class Submarine {

    var position = 0
    var depth = 0

    fun doStep(step: String) {
        val splitted = step.split(' ')
        val command = splitted[0]
        val step = splitted[1].toInt()

        when (command) {
            "forward" -> forward(step)
            "up" -> up(step)
            "down" -> down(step)
        }
    }

    fun result() = position * depth

    private fun forward(step: Int) {
        position += step
    }

    private fun down(step: Int) {
        depth += step
    }

    private fun up(step: Int) {
        depth -= step
    }
}