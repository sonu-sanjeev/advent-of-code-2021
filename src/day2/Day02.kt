package day2

import readInput

sealed class Navigation(val distance: Int) {
    companion object {
        fun fromText(input: String): Navigation {
            val (direction, distance) = input.split(" ")
            return when(direction) {
                "forward" -> FORWARD(distance.toInt())
                "down" -> DOWN(distance.toInt())
                "up" -> UP(distance.toInt())
                else -> error("Unknown direction!")
            }
        }
    }
}

class FORWARD(distance: Int) : Navigation(distance)
class DOWN(distance: Int) : Navigation(distance)
class UP(distance: Int): Navigation(distance)


fun main() {

    val navigation = readInput("day2/Day02")
        .map {
            Navigation.fromText(it)
        }

    fun part1(): Int {
        var hor = 0
        var dep = 0
        navigation.forEach { nav ->
            when (nav) {
                is FORWARD -> hor += nav.distance
                is DOWN -> dep += nav.distance
                is UP -> dep -= nav.distance
            }
        }

        return hor * dep
    }

    fun part2(): Int {
        var hor = 0
        var dep = 0
        var aim = 0

        navigation.forEach { nav ->
            when (nav) {
                is FORWARD -> {
                    hor += nav.distance
                    dep += (aim * nav.distance)
                }
                is DOWN -> aim += nav.distance
                is UP -> aim -= nav.distance
            }
        }

        return hor * dep
    }

    println(part1())
    println(part2())
}