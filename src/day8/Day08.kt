package day8

import readInput

// Reference : https://todd.ginsberg.com/post/advent-of-code/2021/day8/

fun part1() = readInput("day8/Day08").flatMap {
    it.split("| ").last().split(" ")
}.count {
    it.length in setOf(2, 3, 4, 7)
}

class Row(patterns: List<Set<Char>>, private val outputs: List<Set<Char>>) {

    private val numberMap = discoverNumberMap(patterns)

    fun getOutputDigit() = buildString {
        outputs.forEach { op ->
            append(numberMap.getValue(op))
        }
    }.toInt()

    private fun discoverNumberMap(patterns: List<Set<Char>>): Map<Set<Char>, Int> {
        val digit = Array<Set<Char>>(10) { emptySet() }

        //unique length numbers
        digit[1] = patterns.first { it.size == 2 }
        digit[4] = patterns.first { it.size == 4 }
        digit[7] = patterns.first { it.size == 3 }
        digit[8] = patterns.first { it.size == 7 }

        // 3 is length 5 and overlaps 1
        digit[3] = patterns.filter { it.size == 5 }
            .first {
                it.containsAll(digit[1])
            }

        // 9 is length 6 and overlaps 3
        digit[9] = patterns.filter { it.size == 6 }
            .first {
                it.containsAll(digit[3])
            }

        // 0 is length 6, overlaps 1 and 7, and is not 9
        digit[0] = patterns.filter { it.size == 6 }
            .filter {
                it.containsAll(digit[1]) && it.containsAll(digit[7])
            }.first {
                it != digit[9]
            }

        // 6 is length 6 and is not 0 or 9
        digit[6] = patterns.filter { it.size == 6 }
            .first {
                it != digit[0] && it != digit[9]
            }

        // 5 is length 5 and is overlapped by 6
        digit[5] = patterns.filter { it.size == 5 }
            .first {
                digit[6].containsAll(it)
            }

        // 2 is length 5 and is not 3 or 5
        digit[2] = patterns.filter { it.size == 5 }
            .first {
                it != digit[3] && it != digit[5]
            }

        return digit.mapIndexed { index, set ->
            set to index
        }.toMap()
    }
}

val rows = readInput("day8/Day08").map { line ->
    val readings = line.split(" ").filterNot { it == "|" }
    val patterns = readings.take(10).map { it.toSet() }
    val outputs = readings.takeLast(4).map { it.toSet() }
    Row(patterns, outputs)
}


fun part2() = rows.sumOf {
    it.getOutputDigit()
}

fun main() {
    println(part1())
    println(part2())
}

