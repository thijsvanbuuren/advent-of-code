package advent._2021

import advent.util.ADay
import advent.util.Day
import advent.util.hexToBinary
import org.slf4j.LoggerFactory

class Packet(input: String) {
    val headerSize = 6
    val data = input
    val version = data.substring(0, 3).toInt(2)
    val typeId = data.substring(3, 6).toInt(2)
    val contents = data.substring(6)

    var parsedChars: Int = headerSize

    var subPackets = mutableListOf<Packet>()

    init {
//        LoggerFactory.getLogger(this::class.java).debug(this.toString())
    }

    val parsed: Any? = when (typeId) {
        4 -> parseLiteral()
        else -> {
            parseSubs()
            when (typeId) {
                0 -> subPackets.map { it.parsed as? Long ?: 0L }.sum()
                1 -> subPackets.map { it.parsed as? Long ?: 0L }.reduce { a, b -> a * b }
                2 -> subPackets.map { it.parsed as? Long ?: 0L }.minOf { it }
                3 -> subPackets.map { it.parsed as? Long ?: 0L }.maxOf { it }
                5 -> subPackets.map { it.parsed as? Long ?: 0L }.let { if (it[0] > it[1]) 1 else 0 }
                6 -> subPackets.map { it.parsed as? Long ?: 0L }.let { if (it[0] < it[1]) 1 else 0 }
                7 -> subPackets.map { it.parsed as? Long ?: 0L }.let { if (it[0] == it[1]) 1 else 0 }
                else -> null
            }
        }
    }


    private fun parseLiteral(): Long? {
        var i = 0
        var result = ""

        while (i < contents.length) {
            val firstBit = contents[i]
            val data = contents.substring(i + 1, i + 5)
            parsedChars += 5
            result += data
            when (firstBit) {
                '0' -> return result.toLong(2)
                else -> i += 5
            }
        }
        return null
    }

    private fun parseSubs(): String {
        val mode = contents[0]
        parsedChars += 1
        when (mode) {
            '0' -> {
                val lengthInBits = contents.substring(1, 16).toInt(2)
                var i = 0
                parsedChars += 15
                while (i < lengthInBits) {
                    val p = Packet(contents.substring(i + 16))
                    subPackets += p
                    i += p.parsedChars
                }
                parsedChars += i
            }
            '1' -> {
                val nrOfPacketts = contents.substring(1, 12).toInt(2)
                var i = 0
                parsedChars += 11
                (0 until nrOfPacketts).forEach {
                    val p = Packet(contents.substring(12 + i))
                    subPackets += p
                    i += p.parsedChars
                }
                parsedChars += i
            }
        }
        return "?"
    }

    override fun toString(): String {
        var base = " $version - $typeId - $parsed   From: $data "
        subPackets.forEach {
            base += "\n   - $it"
        }
        return base
    }

    fun sumVersions(): Int = version + subPackets.sumOf { it.sumVersions() }
}

@ADay(2021, 16, "day16")
class Day16 : Day() {
    override fun doPart1(input: List<String>): Any {
        return input.first().hexToBinary(4).let { Packet(it) }.sumVersions()
    }

    override fun doPart2(input: List<String>): Any {
        return input.first().hexToBinary(4).let { Packet(it) }.parsed ?: -1
    }

}