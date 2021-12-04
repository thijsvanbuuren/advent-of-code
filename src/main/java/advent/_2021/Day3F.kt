package advent._2021

import advent.util.ADay
import advent.util.Day

@ADay(2021, 3, "day3F")
class Day3F : Day {
    override fun doPart1(input: List<String>): Any {

        var n = 0 // column index
        var aantalKolommen = 12
        var gammaResultaat = arrayListOf<Int>()

        var c0 = 0
        var c1 = 0

        input.forEach {
            (n until aantalKolommen).forEach { n ->  gammaResultaat[n] += Character.getNumericValue(it[n])}
        }


        (n until aantalKolommen).forEach { n ->
            var a = 0
            input.forEach {
                val bit = Character.getNumericValue(it[n])
                a += bit
            }
//            gammaResultaat += if (a > (input.size / 2)) 1 else 0
        }

        var epsilonResultaat = arrayListOf<Int>()
        gammaResultaat.forEach {
            if (it == 0) {
                epsilonResultaat += 1
            } else {
                epsilonResultaat += 0
            }
        }

        val gamma = gammaResultaat.joinToString("").toInt(2)
        val epsilon = epsilonResultaat.joinToString("").toInt(2)
        return gamma * epsilon
    }

    override fun doPart2(input: List<String>): Any {
        return -1
    }
}