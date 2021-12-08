package advent._2021

import advent.util.ADay
import advent.util.Day
import org.slf4j.LoggerFactory

class ExactCompareList<T>(list: List<T>) : ArrayList<T>(list) {
    override fun equals(other: Any?) = this === other
}

@ADay(2021, 8, "day8")
class Day8 : Day {

    companion object {
        val logger = LoggerFactory.getLogger("Day 8")
    }

    class Decoder {
        private val allOptions = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g')
        private val top = ExactCompareList(allOptions)
        private val topLeft = ExactCompareList(allOptions)
        private val topRight = ExactCompareList(allOptions)
        private val middle = ExactCompareList(allOptions)
        private val bottomLeft = ExactCompareList(allOptions)
        private val bottomRight = ExactCompareList(allOptions)
        private val bottom = ExactCompareList(allOptions)
        private val all = listOf(top, topLeft, topRight, middle, bottomLeft, bottomRight, bottom)

        val size5 = mutableListOf<List<Char>>()
        val size6 = mutableListOf<List<Char>>()

        fun parse(signal: String) {
            val chars = signal.toList()
//            logger.debug("Parsing: $signal size: ${signal.length}")
            when (signal.length) {
                2 -> listOf(topRight, bottomRight) // 1
                3 -> listOf(top, topRight, bottomRight) // 7
                4 -> listOf(topLeft, middle, topRight, bottomRight) // 4
                5 -> {
                    size5 += chars
                    emptyList<ExactCompareList<Char>>()
                }
                6 -> {
                    size6 += chars
                    emptyList<ExactCompareList<Char>>()
                }
                7 -> listOf(top, topLeft, topRight, middle, bottomLeft, bottom, bottomRight) // 8
                else -> return
            }.takeIf { it.isNotEmpty() }?.let { lists ->
                val allCopy = ExactCompareList(all)
                allCopy.removeAll(lists)
                lists.forEach {
                    it.retainAll(chars)
                }
                allCopy.forEach { it.removeAll(chars) }
//                logger.debug("Decoder:\n $this")
            }
        }

        fun parse5s() { // 2,3,5
            val same = size5.first().toMutableList()
            size5.forEach { same.retainAll(it) }
//            logger.debug("remains5: $same")

            same.forEach { ch ->
                if (top.contains(ch) && middle.contains(ch).not() && bottom.contains(ch).not()) {
                    (all - listOf(top)).forEach { it.remove(ch) }
                    top.retainAll(listOf(ch))
                }
                if (middle.contains(ch) && top.contains(ch).not() && bottom.contains(ch).not()) {
                    (all - listOf(middle)).forEach { it.remove(ch) }
                    middle.retainAll(listOf(ch))
                }
                if (bottom.contains(ch) && top.contains(ch).not() && middle.contains(ch).not()) {
                    (all - listOf(bottom)).forEach { it.remove(ch) }
                    bottom.retainAll(listOf(ch))
                }
            }
        }

        fun parse6s() { // 0,6,9
            val same = size6.first().toMutableList()
            size6.forEach { same.retainAll(it) }
//            logger.debug("remains6: $same")

            // topLeft, bottom, bottomRight, top
            same.forEach { ch ->
                if (topLeft.contains(ch) && bottom.contains(ch).not()
                    && bottomRight.contains(ch).not() && top.contains(ch).not()
                ) {
                    (all - listOf(topLeft)).forEach { it.remove(ch) }
                    topLeft.retainAll(listOf(ch))
                }
                if (bottom.contains(ch) && topLeft.contains(ch).not()
                    && bottomRight.contains(ch).not() && top.contains(ch).not()
                ) {
                    (all - listOf(bottom)).forEach { it.remove(ch) }
                    bottom.retainAll(listOf(ch))
                }
                if (bottomRight.contains(ch) && topLeft.contains(ch).not()
                    && bottom.contains(ch).not() && top.contains(ch).not()
                ) {
                    (all - listOf(bottomRight)).forEach { it.remove(ch) }
                    bottomRight.retainAll(listOf(ch))
                }
                if (top.contains(ch) && topLeft.contains(ch).not()
                    && bottom.contains(ch).not() && bottomRight.contains(ch).not()
                ) {
                    (all - listOf(top)).forEach { it.remove(ch) }
                    top.retainAll(listOf(ch))
                }
            }
        }

        fun isComplete() = all.find { it.size > 1 } == null

        override fun toString(): String {
            return "top: $top,\n" +
                    "topLeft: $topLeft, \n" +
                    "topRight: $topRight, \n" +
                    "middle: $middle, \n" +
                    "bottomLeft: $bottomLeft, \n" +
                    "bottom: $bottom, \n" +
                    "bottomRight: $bottomRight"
        }

        fun read(digit: String): Int {
            return when (all.filter { digit.contains(it[0]) }.sortedBy { it[0] }) {
                listOf(top, topLeft, topRight, bottomLeft, bottom, bottomRight).sortedBy { it[0] } -> 0
                listOf(topRight, bottomRight).sortedBy { it[0] } -> 1
                listOf(top, topRight, middle, bottomLeft, bottom).sortedBy { it[0] } -> 2
                listOf(top, topRight, middle, bottomRight, bottom).sortedBy { it[0] } -> 3
                listOf(topLeft, topRight, middle, bottomRight).sortedBy { it[0] } -> 4
                listOf(top, topLeft, middle, bottomRight, bottom).sortedBy { it[0] } -> 5
                listOf(top, topLeft, middle, bottomLeft, bottomRight, bottom).sortedBy { it[0] } -> 6
                listOf(top, topRight, bottomRight).sortedBy { it[0] } -> 7
                all.sortedBy { it[0] } -> 8
                listOf(top, topLeft, topRight, middle, bottomRight, bottom).sortedBy { it[0] } -> 9
                else -> {
                    logger.error("ERROR: $digit with \n $this")
                    -1
                }
            }
        }
    }

    override fun doPart1(input: List<String>): Any {
        val data = buildData(input)

        val counts = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

        data.forEach { (signal, digits) ->
            digits.forEach { digit ->
                val nr = when (digit.length) {
                    2 -> 1
                    3 -> 7
                    4 -> 4
                    7 -> 8
                    else -> return@forEach
                }
                counts[nr] = counts[nr] + 1
            }
        }
        return counts.sumOf { it }
    }

    override fun doPart2(input: List<String>): Any {
        val data = buildData(input)

        var result = 0L
        data.forEach { (signals, digits) ->
//        data.first().let { (signals, digits) ->
            val decoder = Decoder()
            signals.sortedBy { it.length }.forEach { signal ->
                decoder.parse(signal)
            }
            decoder.parse5s()
            decoder.parse6s()
//            logger.debug("Decoder: $decoder")
//            logger.debug("complete: ${decoder.isComplete()}")
            val output = digits.map { decoder.read(it) }.joinToString("") { it.toString() }
//            logger.debug("output: $output")
            result += output.toInt()
        }
        return result
    }

    private fun buildData(input: List<String>) = input.map {
        it.split(" | ").let {
            it[0].split(" ") to it[1].split(" ")
        }
    }
}