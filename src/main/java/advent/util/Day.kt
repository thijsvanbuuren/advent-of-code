package advent.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ADay(val year: Int, val day: Int, val fileKey: String)

interface Day {

    val logger: Logger get() = LoggerFactory.getLogger(this.javaClass)

    fun doPart1(input: List<String>): Any
    fun doPart2(input: List<String>): Any
}
