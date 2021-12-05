package day3

import readInput

val lines = readInput("day3/Day03")
val bitIndices = lines[0].indices

fun List<String>.charactersForColumn(n: Int) =
    this.groupingBy {
        it[n]
    }.eachCount()


fun String.invertBinaryString() = this.map {
    if (it == '0') '1' else '0'
}.joinToString("")

fun part1() {
    val charFrequencyByColumn = bitIndices.map {
        lines.charactersForColumn(it)
    }
    val gammaRate = charFrequencyByColumn.joinToString("") { frequencies ->
        val mostFreqChar = frequencies.maxByOrNull { it.value }?.key ?: error("Should find maximum in $frequencies!")
        mostFreqChar.toString()
    }
    val epsilonRate = gammaRate.invertBinaryString()
    println(gammaRate.toInt(2) * epsilonRate.toInt(2))
}

fun part2() {
    val oxygenRating = lines.oxygenGeneratorRating(0)
    val co2scrubberRating = lines.co2scrubberRating(0)
    val lifeSupportRating = oxygenRating * co2scrubberRating
    println(oxygenRating)
    println(co2scrubberRating)
    println(lifeSupportRating)
}

// This function assumes that the input given will always have a single item at the last bit position (here the parameter 'n' is the bit position).
fun List<String>.oxygenGeneratorRating(n: Int): Int {
    if (this.size == 1) return this[0].toInt(2)

    val (zeros, ones) = getMapOfOnesAndZeros(n)

    return if (zeros.size > ones.size) {
        zeros.oxygenGeneratorRating(n + 1)
    } else {
        ones.oxygenGeneratorRating(n + 1)
    }
}

fun List<String>.co2scrubberRating(n: Int): Int {
    if (this.size == 1) return this[0].toInt(2)

    val (zeros, ones) = getMapOfOnesAndZeros(n)

    return if (zeros.size <= ones.size) {
        zeros.co2scrubberRating(n + 1)
    } else {
        ones.co2scrubberRating(n + 1)
    }
}

private fun List<String>.getMapOfOnesAndZeros(n: Int): Pair<List<String>, List<String>> {
    val separatedMap = this.groupBy { it[n] }
    val zeros = separatedMap.getValue('0')
    val ones = separatedMap.getValue('1')
    return Pair(zeros, ones)
}

fun main() {
    part1()
    part2()
}

