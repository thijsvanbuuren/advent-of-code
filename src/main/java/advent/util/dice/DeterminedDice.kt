import advent.util.dice.Dice

class DeterminedDice : Dice {
    var roll = 1
    var rolled = 0

    override fun roll(times: Int): Int {
        var result = 0
        repeat(times) {
            result += roll
            roll++
        }
        rolled += times
        return result
    }
}