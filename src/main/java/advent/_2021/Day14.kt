package advent

import advent.util.ADay
import advent.util.Day
import java.util.*

@ADay(2021, 14, "day14")
class Day14 : Day {
    val maxDepth = 40
    val maxDepthP1 = 10

    override fun doPart1(input: List<String>): Any {
        val template = input.first()

        val rules = input.subList(2, input.size).map { it.split(" -> ").let { it[0] to it[1] } }

        var result = template
        repeat(maxDepthP1) {
            result = processRules(result, rules)
//            logger.debug("$result")
        }
        val countMap = mutableMapOf<Char, Int>()
        result.forEach { countMap[it] = countMap.getOrDefault(it, 0) + 1 }
        val highest = countMap.maxByOrNull { it.value.toLong() } ?: return -1
        val lowest = countMap.minByOrNull { it.value.toLong() } ?: return -1
        logger.debug("high: $highest low: $lowest")
        return highest.value - lowest.value
    }

    private fun processRules(
        base: String,
        rules: List<Pair<String, String>>
    ): String {
        var newResult = base
        val indexes = rules.flatMap { findIndexes(base, it) }
            .filter { it.first != 0 }
        indexes
            .sortedByDescending { it.first }
            .onEach { newResult = "${newResult.substring(0, it.first)}${it.second}${newResult.substring(it.first)}" }
        return newResult
    }

    private fun findIndexes(
        base: String,
        it: Pair<String, String>
    ): List<Pair<Int, String>> {
        val result = mutableListOf<Pair<Int, String>>()
        var fromIndex = 0
        while (fromIndex < base.length) {
            fromIndex = base.indexOf(it.first, fromIndex) + 1
            if (fromIndex == 0) return result
            result += fromIndex to it.second
        }
        return result
    }

    override fun doPart2(input: List<String>): Any {
        val template = input.first()

        val rules = input.subList(2, input.size).map { it.split(" -> ").let { it[0] to it[1] } }

        val stringMap = rules.map { it.first to (it.first[0] + it.second + it.first[1]) }.toMap()

        var d = 7
        repeat(15) {
            d *= 2
        }
        logger.debug("TIME IT TAKES: ${d / 60 / 60 / 24}")
        //cnbc
        //canabac

        //cnbc
        //cn  -> chn
        //check left ch

        var result = template.first().toString()

        var v1 = false
        var resultMap = mapOf<Char, Long>()
            val calculated = mutableMapOf<Pair<String, Int>, Map<Char, Long>>()
        if (v1) {
            val countMap = mutableMapOf<Char, Long>()
            resultMap = countProcessPart2(template, stringMap, maxDepth, countMap)
        } else {
            resultMap = template.windowed(2)
                .map {
                    calc(it, 0, maxDepth, stringMap, calculated)
                }
                .reduce { a, b ->
                    mergeMap(a, b)
                }
        }

//        logger.debug(result)


        val highest = resultMap.maxByOrNull { it.value } ?: return -1
        val lowest = resultMap.minByOrNull { it.value } ?: return -1
        logger.debug("high: $highest low: $lowest")
        return highest.value - lowest.value
    }

    private fun mergeMap(
        a: Map<Char, Long>,
        b: Map<Char, Long>
    ): MutableMap<Char, Long> {
        val result = a.toMutableMap()
        b.forEach {
            result[it.key] = a.getOrDefault(it.key, 0) + it.value
        }
        return result
    }

    private fun countProcessPart2(
        template: String,
        stringMap: Map<String, String>,
        maxDepth: Int,
        iniMap: MutableMap<Char, Long>
    ): MutableMap<Char, Long> {
        var countMap = iniMap
        countMap[template.first()] = 1
        var depth = 0
        var index = 0
//        var result = template.first().toString()
        var left = template.first()
        val rightStack = Stack<Pair<Char, Int>>()
        val calculated = mutableMapOf<Pair<String, Int>, Map<Char, Long>>()

        //abcd
        //ab -> acb
        // b,c,
        // ac
        while (true) {
            val isNew = rightStack.isEmpty()
            var right = (if (isNew) {
//                logger.debug("TEMPLATE: ${index + 1}")
                depth = 0
                template.getOrNull(++index)
            } else {
                // logger.debug("${rightStack.size}")
                val record = rightStack.pop()
                depth = record.second
                record.first
            }) ?: break

            val pair = "$left$right"


            // otherwise calculated
            val internalCountMap = mutableMapOf<Char, Long>()
            val replace = stringMap[pair]
            if (replace != null) {
                if ((++depth) == maxDepth) {
                    /*result += */replace.substring(1).also {
                        internalCountMap[it[0]] = internalCountMap.getOrDefault(it[0], 0) + 1L
                        internalCountMap[it[1]] = internalCountMap.getOrDefault(it[1], 0) + 1L
                    }
                    left = replace[2]
                } else {
                    rightStack.push(replace[2] to depth)
                    rightStack.push(replace[1] to depth)
                }
            } else {
//                result += pair
                internalCountMap[left] = internalCountMap.getOrDefault(left, 0) + 1L
                internalCountMap[right] = internalCountMap.getOrDefault(right, 0) + 1L
                left = right
            }
            countMap = mergeMap(countMap, internalCountMap)
        }
        return countMap
//        logger.debug("$template -> $result")
    }

    fun calc(
        pair: String,
        depth: Int,
        maxDepth: Int,
        rules: Map<String, String>,
        cache: MutableMap<Pair<String, Int>, Map<Char, Long>>
    ): Map<Char, Long> {
        val cacheKey = pair to depth
        val cachedMap = cache[cacheKey]
        if (cachedMap != null) return cachedMap

        val replace = rules[pair]
        val result: Map<Char, Long> = if (depth == maxDepth || replace == null) {
            mapOf(pair[1] to 1L)
        } else {
            mergeMap(
                calc(replace.substring(0, 2), depth + 1, maxDepth, rules, cache),
                calc(replace.substring(1), depth + 1, maxDepth, rules, cache)
            )
        }

        cache[cacheKey] = result
        return result
    }
}