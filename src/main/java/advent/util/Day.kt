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

/**
 * Note: Watch out, parameters are not immutable!
 */
abstract class ParsedDay<T>(val parser: (List<String>) -> T, debug: Boolean = false) : Day(debug) {
    var parsedData: T? = null

    override fun doPart1(input: List<String>) = doPart1Parsed(parser(input).also {
        parsedData = it
    })

    abstract fun doPart1Parsed(input: T): Any
    override fun doPart2(input: List<String>) = doPart2Parsed(parsedData ?: parser(input))
    abstract fun doPart2Parsed(input: T): Any

}
