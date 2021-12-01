package advent._2019.opcode

import org.slf4j.LoggerFactory

enum class ParameterMode {
    REFERENCE, VALUE, RELATIVE;

    companion object {
        fun fromLong(number: Long?) = when (number) {
            1L -> VALUE
            2L -> RELATIVE
            else -> REFERENCE
        }
    }
}

class OpcodeV2(
    inputMemory: List<Long>,
    val input: MutableList<Long>,
    private val enableLog: Boolean
) {

    private val logger = LoggerFactory.getLogger("OpcodeV2")

    private val memory = inputMemory.mapIndexed { index, value -> (index.toLong() to value) }.toMap().toMutableMap()

    private var relativeBase = 0L

    private var instruction: Long = -1
    private var pointer: Long = 0L

    private var inputCounter = 0

    var output: MutableList<Long> = mutableListOf()

    private val instructionMap = mapOf<Long, () -> Long>(
        1L to ::add,
        2L to ::multiply,
        3L to ::readInput,
        4L to ::writeOutput,
        5L to ::moveIfNot0,
        6L to ::moveIf0,
        7L to ::smallerThan,
        8L to ::equals,
        9L to ::setRelativeBase,
        99L to ::halt
    )

    fun process() {
        while (pointer >= 0) {
            instruction = memory[pointer] ?: throw IllegalStateException()

            val opCode = findOpCode()
            pointer = instructionMap[opCode]
                ?.invoke()
                ?: throw IllegalStateException("Opcode not found '$opCode'")
        }
    }

    fun addInput(additionalInput: Long) {
        input.add(additionalInput)
    }

    fun lastOutput() = output.last()

    private fun setRelativeBase(): Long = single { relativeBase += get(1) }
    private fun readInput(): Long = single { set(1, input[inputCounter]); inputCounter++ }
    private fun writeOutput(): Long = single { output.add(get(1)) }
        .also { if (enableLog) logger.info(lastOutput().toString()) }

    private fun moveIf0(): Long = moveIf { it == 0L }
    private fun moveIfNot0(): Long = moveIf { it != 0L }

    private fun equals(): Long = calc { a, b -> a == b }
    private fun smallerThan(): Long = calc { a, b -> a < b }
    private fun add(): Long = calc { a, b -> a + b }
    private fun multiply(): Long = calc { a, b -> a * b }

    private fun halt() = -1L

    private inline fun single(doIt: () -> Unit) = (pointer + 2)
        .also { doIt() }

    private inline fun moveIf(doIt: (a: Long) -> Boolean) = if (doIt(get(1))) get(2) else (pointer + 3)
    private inline fun calc(doIt: (a: Long, b: Long) -> Any) = (pointer + 4)
        .also { set(3, doIt(get(1), get(2))) }

    private fun findOpCode(): Long = instruction % 100
    private fun findOpMode(parameterNumber: Long): ParameterMode {
        val textValue = instruction.toString()
        val characterDistanceFromEnd = 2 + parameterNumber
        val value = textValue.getOrNull((textValue.length - characterDistanceFromEnd).toInt())?.toString()?.toLong()
        return ParameterMode.fromLong(value)
    }

    private operator fun get(parameterNumber: Long): Long {
        val param = memory[pointer + parameterNumber] ?: 0
        return when (findOpMode(parameterNumber)) {
            ParameterMode.REFERENCE -> memory[param] ?: 0
            ParameterMode.VALUE -> param
            ParameterMode.RELATIVE -> memory[relativeBase + param] ?: 0
        }
    }

    private operator fun set(parameterNumber: Long, value: Any) {
        val param = memory[pointer + parameterNumber] ?: 0
        val realValue = when (value) {
            is Long -> value
            is Boolean -> if (value) 1L else 0L
            else -> throw UnsupportedOperationException("Unable to store value of type; ${value.javaClass}")
        }
        when (findOpMode(parameterNumber)) {
            ParameterMode.RELATIVE -> memory[relativeBase + param] = realValue
            else -> memory[param] = realValue
        }
    }
}
