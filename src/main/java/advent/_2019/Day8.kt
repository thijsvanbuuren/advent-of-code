package advent._2019

import advent.util.ADay
import advent.util.Day

class Layer(val data: Array<IntArray>, val width: Int, val height: Int) {

    fun count(number: Int) = data.map { it.count { it == number } }.sum()

    fun combine(layer: Layer) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (data[x][y] == 2) {
                    data[x][y] = layer.data[x][y]
                }
            }
        }
    }

    override fun toString(): String {
        var result = "\r\n"
        for (y in 0 until height) {
            for (x in 0 until width) {
                result += when (data[x][y]) {
                    1 -> '\u2588'
                    0 -> ' '
                    2 -> '\u2591'
                    else -> '-'
                }
            }
            result += "\r\n"
        }
        return result
    }
}

class Image(val width: Int, val height: Int) {
    val layers = mutableListOf<Layer>()

    fun load(inputData: String) {
        var index = 0
        while (index < inputData.length) {
            layers.add(buildLayer(inputData, index))
            index += (width * height)
        }
    }

    fun combineLayers(): Layer {
        val result = Layer(layers[0].data, width, height)
        for (i in 1 until layers.size) {
            result.combine(layers[i])
        }
        return result;
    }

    fun findLowest0Layers(): Layer {
        var result: Layer? = null
        var currentCount = Int.MAX_VALUE
        layers.forEach {
            val count = it.count(0)
            if (count < currentCount) {
                currentCount = count
                result = it
            }
        }
        return result!!
    }

    private fun buildLayer(inputData: String, index: Int): Layer {
        var i = index
        val data = Array(width) { IntArray(height) }
        for (y in 0 until height) {
            for (x in 0 until width) {
                data[x][y] = (inputData[i] - '0')
                i++
            }
        }
        return Layer(data, width, height)
    }
}

@ADay(2019, 8, "day8")
class Day8 : Day {

    override fun doPart1(data: List<String>): Any {
        val image = Image(25, 6)
        image.load(data[0])

        val lowestLayer = image.findLowest0Layers()
        return lowestLayer.count(1) * lowestLayer.count(2)
    }

    override fun doPart2(data: List<String>): Any {
        val image = Image(25, 6)
        image.load(data[0])
        return image.combineLayers()
    }

}