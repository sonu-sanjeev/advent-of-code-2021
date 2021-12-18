package day9

import readInput

class HeightMap(private val points: Map<Pair<Int, Int>, Int>) {
    companion object {
        fun from(input: List<List<Int>>): HeightMap {
            val points = HashMap<Pair<Int, Int>, Int>()
            input.forEachIndexed { x, line ->
                line.forEachIndexed { y, num ->
                    points[Pair(x, y)] = num
                }
            }
            return HeightMap(points)
        }
    }

    fun findLowPointSum() = findLowPoints().values.sumOf { it + 1 }

    private fun findLowPoints() = points.filter {
        isLowestNumber(it)
    }

    private fun isLowestNumber(it: Map.Entry<Pair<Int, Int>, Int>): Boolean {
        val (x, y) = it.key
        points[Pair(x, y - 1)]?.let { left ->
            if (it.value >= left) return false
        }
        points[Pair(x, y + 1)]?.let { right ->
            if (it.value >= right) return false
        }
        points[Pair(x - 1, y)]?.let { top ->
            if (it.value >= top) return false
        }
        points[Pair(x + 1, y)]?.let { bottom ->
            if (it.value >= bottom) return false
        }
        return true
    }

    private fun Pair<Int, Int>.neighbours(): List<Pair<Int, Int>> {
        val (x, y) = this
        return listOf(Pair(x, y - 1), Pair(x, y + 1), Pair(x - 1, y), Pair(x + 1, y))
    }

    private tailrec fun measureBasin(basin: ArrayList<Pair<Int, Int>>, index: Int): List<Pair<Int, Int>> {
        if (index >= basin.size) return basin

        val newBasins = ArrayList<Pair<Int, Int>>()
        basin.forEach {
            newBasins.addAll(it.neighbours().filterNot { pair ->
                points[pair] == null || points[pair] == 9 || basin.containsPair(pair) || newBasins.containsPair(pair)
            })
        }
        basin.addAll(newBasins)
        return measureBasin(basin, index + 1)
    }

    fun largestBasinArea() = findLowPoints().map {
        measureBasin(arrayListOf(it.key), 0).size
    }.sorted().takeLast(3).reduce { acc, i ->
        acc * i
    }
}

private fun ArrayList<Pair<Int, Int>>.containsPair(pair: Pair<Int, Int>) = any {
    it.first == pair.first && it.second == pair.second
}


val input = readInput("day9/Day09").map {
    it.toCharArray().map { char ->
        char.toString().toInt() // We need to convert char to string, otherwise the result will be char code.
    }
}

fun part1() = HeightMap.from(input).findLowPointSum()
fun part2() = HeightMap.from(input).largestBasinArea()

fun main() {
    println(part1())
    println(part2())
}