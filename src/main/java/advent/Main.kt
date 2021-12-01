package advent

import advent.util.Runner

object Main {

    private const val runAll = true
    private const val year = 2021

    @JvmStatic
    fun main(args: Array<String>) {
        if (runAll) Runner.runAll()
        else Runner.runYear(year)
    }
}