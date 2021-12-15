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
        logger.warn("Starting YEAR: $year")

        val days = yearData.entries
            .sortedBy { it.key.day }

        if (dayNr == -1) {
            days.forEach { (aDay, day) -> runDay(year, aDay, day) }
        } else {
            days.find { (aDay, day) -> aDay.day == dayNr }!!.let { (aDay, day) -> runDay(year, aDay, day ) }
        }

        logger.warn("Ending Year: $year")
    }

    private fun runDay(year: Int, aDay: ADay, day: Day) {
        val url = javaClass.classLoader.getResource("$year/${aDay.fileKey}.txt")
        val input = File(url.toURI()).readLines()

        logger.warn(" - Day ${aDay.day} with file: ${aDay.fileKey} ")
        runPart(1) { day.doPart1(input) }
        runPart(2) { day.doPart2(input) }
    }

    private fun runPart(part: Int, block: () -> Any) {
        var result: Any?
        measureTimeMillis {
            result = block()
        }.also { logger.info("   - Part $part : $result, with time $it") }
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
}