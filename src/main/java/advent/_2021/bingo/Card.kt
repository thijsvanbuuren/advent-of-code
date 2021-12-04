package advent._2021.bingo

class Card(data: Array<IntArray>) {

    val valueMap = mutableMapOf<Int, Pair<Int, Int>>()
    val marked = mutableListOf<Pair<Int, Int>>()

    val columnMarks = IntArray(5) { 0 }
    val rowMarks = IntArray(5) { 0 }

    init {
        data.forEachIndexed { row, columns ->
            columns.forEachIndexed { column, value ->
                valueMap[value] = row to column
            }
        }
    }

    fun runNumber(number: Int): Boolean {
        valueMap[number]?.let { pair: Pair<Int, Int> ->
            marked += pair
            rowMarks[pair.first] = rowMarks[pair.first] + 1
            columnMarks[pair.second] = columnMarks[pair.second] + 1
            return rowMarks[pair.first] == 5 || columnMarks[pair.second] == 5
        }
        return false
    }

    fun score(): Int {
        var score = 0
        valueMap.forEach { value, location ->
            if (marked.contains(location)) return@forEach

            score += value
        }
        return score
    }
}