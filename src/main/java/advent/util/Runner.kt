package advent.util

import advent.Day0
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.reflect.full.createInstance
import kotlin.system.measureTimeMillis

object Runner {
    private val logger: Logger = LoggerFactory.getLogger("RUNNER")

    private val adventsMap = mutableMapOf<Int, MutableMap<ADay, Day>>()

    val columnSizes = listOf(20, 20, 20)

    init {
        buildMap()
    }

    fun runAll() {
        adventsMap.keys
            .sorted()
            .forEach { runYear(it) }
    }

    fun runYear(year: Int, dayNr: Int = -1) {
        val yearData = adventsMap[year] ?: return

        logger.error("-------------------------------------------------------------")
        logger.error(toColumns(" Year: $year", "Part 1", "Part 2"))

        val days = yearData.entries
            .sortedBy { it.key.day }

        if (dayNr == -1) {
            days.forEach { (aDay, day) -> runDay(year, aDay, day) }
        } else {
            days.find { (aDay, day) -> aDay.day == dayNr }!!.let { (aDay, day) -> runDay(year, aDay, day) }
        }

    }

    private fun runDay(year: Int, aDay: ADay, day: Day) {
        val url = javaClass.classLoader.getResource("$year/${aDay.fileKey}.txt")
        val input = File(url.toURI()).readLines()

        val p1 = runPart(1) { day.doPart1(input) }
        val p2 = runPart(2) { day.doPart2(input) }
        val output = toColumns(
            "- Day ${aDay.day} (${aDay.fileKey})",
            "${p1.first} (${p1.second})",
            "${p2.first} (${p2.second})"
        )
        if (p1.first == -1 || p2.first == -1) {
            logger.error(output)
        } else logger.info(output)
    }

    private fun runPart(part: Int, block: () -> Any): Pair<Any, Long> {
        var result: Any?
        val time = measureTimeMillis {
            result = block()
        }
        return result!! to time
    }

    private fun buildMap() {
        listClasses<ADay>()
            .map { it.createInstance() }
            .filterIsInstance<Day>()
            .filter { it !is Day0 }
            .forEach { day ->
                val meta = day::class.annotations.firstOrNull { it is ADay } as ADay? ?: return@forEach

                val yearMap = adventsMap.getOrDefault(meta.year, mutableMapOf())
                adventsMap[meta.year] = yearMap

                yearMap[meta] = day
            }
    }

    fun toColumns(c1: String, c2: String, c3: String): String = " | " +
            c1.padEnd(columnSizes[0]) + " | " +
            c2.padEnd(columnSizes[1]) + " | " +
            c3.padEnd(columnSizes[2]) + " | "
}