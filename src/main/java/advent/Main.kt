package advent

import advent.util.Runner

object Main {

    private const val runAll = false
    private const val year = 2022
    // Use -1 to run whole year
    private const val day = -1

    @JvmStatic
    fun main(args: Array<String>) {
        if (runAll) Runner.runAll()
        else Runner.runYear(year, day)
    }
}