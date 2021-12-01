package advent._2019.opcode

object OpcodeV1 {

    fun process(values: MutableList<Int>): MutableList<Int> {
        var index = 0;
        var instructionValues = 4;
        while (index < values.size) {
            when (values[index]) {
                1 -> {
                    values[values[index + 3]] = values[values[index + 1]] + values[values[index + 2]]
                }
                2 -> {
                    values[values[index + 3]] = values[values[index + 1]] * values[values[index + 2]]
                }
                99 -> {
                    instructionValues = 1
                    return values
                }
            }
            index += instructionValues
        }
        return mutableListOf()
    }
}