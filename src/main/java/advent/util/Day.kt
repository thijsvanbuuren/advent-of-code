package advent.util

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ADay(val year: Int, val day: Int, val fileKey: String)

interface Day {
    fun doPart1(input: List<String>): Any
    fun doPart2(input: List<String>): Any
}
