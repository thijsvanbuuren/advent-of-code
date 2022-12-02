package advent.util.dice

interface Dice {
    fun roll(times: Int = 1): Int
}