package day5

import readInput
import java.lang.Math.abs
import java.lang.Math.max

data class Point(val x: Int, val y: Int)

// Reference : https://gitlab.com/Nohus/adventofcode2021/-/blob/master/src/main/kotlin/day5_2/Solution.kt

val input = readInput("day5/Day05").map {
    it.split(" -> ").map { items ->
        val points = items.split(",", limit = 2)
        Point(x = points[0].toInt(), y = points[1].toInt())
    }
}

fun part1() {
    val result = input.filter {
        it[0].x == it[1].x || it[0].y == it[1].y
    }.flatMap { (start, end) ->
        findLine(start, end)
    }.groupBy {
        it
    }.count { it.value.size > 1 }

    println(result)
}

fun part2() {
    val result = input.flatMap { (start, end) ->
        findLine(start, end)
    }.groupBy {
        it
    }.count { it.value.size > 1 }

    println(result)
}

fun findLine(a: Point, b: Point): List<Point> {
    return (0..max(abs(a.x - b.x), abs(a.y - b.y))).map { step ->
        val x = if (b.x > a.x) a.x + step else if (b.x < a.x) a.x - step else a.x
        val y = if (b.y > a.y) a.y + step else if (b.y < a.y) a.y - step else a.y
        Point(x, y)
    }
}

fun main() {
    part1()
    part2()
}