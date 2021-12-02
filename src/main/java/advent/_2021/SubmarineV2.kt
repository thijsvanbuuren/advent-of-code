package advent._2021

class SubmarineV2 {

    var position = 0
    var depth = 0
    var aim = 0

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
        depth += (aim * step)
    }

    private fun down(step: Int) {
        aim += step
    }

    private fun up(step: Int) {
        aim -= step
    }
}