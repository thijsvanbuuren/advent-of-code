package advent._2021

import DeterminedDice
import advent.util.ADay
import advent.util.Day
import advent.util.dice.Dice

@ADay(2021, 21, "day21")
class Day21 : Day(false) {
    val options3D3 = mutableListOf<Int>()

    init {
        (1..3).forEach { d1 ->
            (1..3).forEach { d2 ->
                (1..3).forEach { d3 ->
                    options3D3 += (d1 + d2 + d3)
                }
            }
        }
    }

    override fun doPart1(input: List<String>): Any {
        val dDie = DeterminedDice()
        val players = input
            .map { it.split(" starting position: ") }
            .map { Player(it[0], it[1].toInt()) }
        log(players.toString())

        // winner to loser
        val result = Game(players, dDie, 3).run()
        return result.second.score * dDie.rolled
    }

    override fun doPart2(input: List<String>): Any {
        val players = input
            .map { it.split(" starting position: ") }
            .map { Player(it[0], it[1].toInt(), 0, 6) }
        log(players.toString())

        val bucket = HashSet<Pair<Player, Player>>()
        bucket += players[0] to players[1]
        var player1Wins = 0L
        var player2Wins = 0L
        var player1 = true
        while (bucket.isNotEmpty()) {
            bucket.toSet().forEach { pair ->
                if (player1) {
                    player1Wins += split(pair, { it.first }, bucket)
                } else {
                    player2Wins += split(pair, { it.second }, bucket)
                }
            }
            log("bucket size: ${bucket.size}")
            player1 = !player1
        }

        return "$player1Wins -> $player2Wins"
    }

    private fun split(
        gamePair: Pair<Player, Player>,
        getPlayer: (Pair<Player, Player>) -> Player,
        bucket: MutableSet<Pair<Player, Player>>
    ): Int {
        var wins = 0
        val copies = mutableListOf(gamePair)

        repeat(options3D3.size - 1) {
            copies += gamePair.first.copy() to gamePair.second.copy()
        }
        copies.forEachIndexed { index, pair ->
            if (index > 0) bucket += pair
            getPlayer(pair).move(options3D3[index]).also { result ->
                if (result) {
                    wins++
                    bucket.remove(pair)
                }
            }
        }
        return wins
    }
}

class Game(val players: List<Player>, val dice: Dice, val rollTimes: Int) {
    fun run(): Pair<Player, Player> {
        var winner: Player? = null
        while (winner == null) {
            winner = players.find { it.move(dice.roll(rollTimes)) }
        }
        val loser = if (winner == players[0]) players[1] else players[0]
        return winner to loser
    }
}

// positions 1-10
data class Player(val name: String, var position: Int, var score: Int = 0, val targetScore: Int = 1000) {
    fun move(forward: Int): Boolean {
        position = (position + forward) % 10
        if (position == 0) position = 10
        score += position
        return score >= targetScore
    }

    override fun toString(): String {
        return "$name: p:$position s:$score"
    }

    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun hashCode(): Int = 1
}