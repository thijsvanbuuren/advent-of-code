package advent

import advent.util.Runner

object Main {

    private const val runAll = false
    private const val year = 2021
    private const val day = 16

    @JvmStatic
    fun main(args: Array<String>) {
        if (runAll) Runner.runAll()
        else Runner.runYear(year, day)
    }
}