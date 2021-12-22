package advent.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ADay(val year: Int, val day: Int, val fileKey: String)

abstract class Day(val debug: Boolean = false) {

    private val logger: Logger get() = LoggerFactory.getLogger(this.javaClass)

    fun log(output: String) {
        if (debug.not()) return
        logger.debug(output)
    }

    abstract fun doPart1(input: List<String>): Any
    abstract fun doPart2(input: List<String>): Any
}
