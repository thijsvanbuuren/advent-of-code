package advent._2022

import advent._2022.RPC.*
import advent.util.ADay
import advent.util.ParsedDay

@ADay(2022, 2, "day2")
class Day2 : ParsedDay<List<RPCGame>>({ input ->
    val moves = RPC.values().flatMap { move -> move.keys.map { it to move } }.toMap()
    input.map { line ->
        val splitted = line.split(" ")
        RPCGame(
            moves[splitted[0].first()] ?: throw Exception("help"),
            moves[splitted[1].first()] ?: throw Exception("help"),
            when (splitted[1].first()) {
                'X' -> -1
                'Y' -> 0
                'Z' -> 1
                else -> throw Exception("help")
            }
        )
    }
}, false) {
    override fun doPart1Parsed(input: List<RPCGame>): Any {
        input.forEach { log("Points: " + it.points) }
        return input.sumOf { it.points }
    }

    override fun doPart2Parsed(input: List<RPCGame>): Any {
        input.forEach { log("Points: " + it.pointsV2) }
        return input.sumOf { it.pointsV2 }
    }
}

enum class RPC(val keys: String, val score: Int) {
    Rock("AX", 1),
    Paper("BY", 2),
    Scissor("CZ", 3)
}

class RPCGame(val opponent: RPC, val you: RPC, val ending: Int) {

    val won: Int = when (opponent to you) {
        Rock to Paper -> 1
        Rock to Scissor -> -1

        Paper to Rock -> -1
        Paper to Scissor -> 1

        Scissor to Rock -> 1
        Scissor to Paper -> -1

        else -> 0
    }

    val neededMove: RPC = when (opponent to ending) {
        Rock to 1 -> Paper
        Rock to -1 -> Scissor

        Paper to -1 -> Rock
        Paper to 1 -> Scissor

        Scissor to 1 -> Rock
        Scissor to -1 -> Paper

        else -> opponent
    }

    val points = you.score + when {
        won > 0 -> 6
        won == 0 -> 3
        else -> 0
    }

    val pointsV2 = neededMove.score + when {
        ending > 0 -> 6
        ending == 0 -> 3
        else -> 0
    }
}
