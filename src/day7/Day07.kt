package day7

import readInput
import kotlin.math.abs
import kotlin.math.min

fun solution(input: List<Int>, cost: (p1: Int, p2: Int) -> Int): Int {
    var minDistance = Int.MAX_VALUE
    for (i in input.indices) {
        val sum = input.sumOf {
            cost(it, input[i])
        }
        minDistance = min(minDistance, sum)
    }
    return minDistance
}

fun main() {
    val input = readInput("day7/Day07")
        .flatMap {
            it.split(",").map { numberString ->
                numberString.toInt()
            }
        }

    // Part 1
    println(solution(input) { p1, p2 ->
        abs(p1 - p2)
    })

    // Part 2
    println(solution(input) { p1, p2 ->
        val n = abs(p1 - p2)
        n * (n + 1) / 2
    })
}