package day6

import readInput


val input = readInput("day6/Day06").flatMap {
    it.split(",").map { numberString ->
        numberString.toInt()
    }
}

fun solution(days: Int): Long {
    val map = input
        .groupBy { it }
        .mapValues { entry ->
            entry.value.size.toLong()
        }.toMutableMap()

    repeat(days) {
        val zeroCount = map.getOrDefault(0, 0)
        for (i in 0..7) {
            map[i] = map.getOrDefault(i + 1, 0)
        }
        map[6] = map.getOrDefault(6, 0) + zeroCount
        map[8] = zeroCount
    }

    return map.values.sum()
}

fun main() {
    println(solution(80))
    println(solution(256))
}