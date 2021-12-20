package advent.util

fun String.hexToBinary(size: Int) = this.split("")
    .filter { it.isNotEmpty() }
    .map { Integer.toBinaryString(it.toInt(16)) }
    .map { it.padStart(size, '0') }
    .joinToString("")