package advent._2021

import advent._2021.bingo.Card
import advent.util.ADay
import advent.util.Day
import org.slf4j.LoggerFactory

@ADay(2021, 4, "day4")
class Day4 : Day {

    override fun doPart1(input: List<String>): Any {
        val iterator = input.iterator()

        val numbers = iterator.next().split(",").map { it.toInt() }

        val cards = buildCards(iterator)

        for (number in numbers) {
            cards.forEach {
                if (it.runNumber(number)) {
                    return it.score() * number
                }
            }
        }
        return -1
    }

    override fun doPart2(input: List<String>): Any {
        val iterator = input.iterator()

        val numbers = iterator.next().split(",").map { it.toInt() }

        val cards = buildCards(iterator)

        var lastScore = -1
        for (number in numbers) {
            ArrayList(cards).forEach {
                if (it.runNumber(number)) {
                    lastScore = it.score() * number
//                    logger.debug("Score: $lastScore")
                    cards.remove(it)
                }
            }
        }
        return lastScore
    }

    private fun buildCards(iterator: Iterator<String>): MutableList<Card> {
        val gridSize = 5
        var row = 0
        var cardData = Array(gridSize) { IntArray(5) }

        val cards = mutableListOf<Card>()

        while (iterator.hasNext()) {
            val line = iterator.next()
            if (line.isBlank()) continue

            cardData[row] = line.split(" ").filter { it.isNotBlank() }.map { it.trim().toInt() }.toIntArray()
            row++
            if (row == gridSize) {
                cards += Card(cardData)
                cardData = Array(gridSize) { IntArray(5) }
                row = 0
            }
        }
        return cards
    }
}