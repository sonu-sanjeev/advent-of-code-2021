package day2

import readInput

// Inspired from jetbrains AOC code samples.

data class SimpleSubmarineState(val hor: Int, val dep: Int)
data class AimedSubmarineState(val hor: Int, val dep: Int, val aim: Int)

sealed class Instruction(val distance: Int) {
    abstract fun execute(state: SimpleSubmarineState): SimpleSubmarineState
    abstract fun execute(state: AimedSubmarineState): AimedSubmarineState

    companion object {
        fun fromInputText(input: String): Instruction {
            val (direction, distanceText) = input.split(" ", limit = 2)
            val distance = distanceText.toInt()
            return when (direction) {
                "forward" -> Forwards(distance)
                "down" -> Downward(distance)
                "up" -> Upward(distance)
                else -> error("Unknown direction!")
            }
        }
    }
}

class Forwards(distance: Int) : Instruction(distance) {
    override fun execute(state: SimpleSubmarineState) =
        state.copy(hor = state.hor + distance)

    override fun execute(state: AimedSubmarineState) =
        state.copy(hor = state.hor + distance, dep = state.dep + (state.aim * distance))
}

class Downward(distance: Int) : Instruction(distance) {
    override fun execute(state: SimpleSubmarineState) =
        state.copy(dep = state.dep + distance)

    override fun execute(state: AimedSubmarineState) =
        state.copy(aim = state.aim + distance)
}

class Upward(distance: Int) : Instruction(distance) {
    override fun execute(state: SimpleSubmarineState) =
        state.copy(dep = state.dep - distance)

    override fun execute(state: AimedSubmarineState) =
        state.copy(aim = state.aim - distance)
}

fun main() {
    val instructions = readInput("day2/Day02")
        .map {
            Instruction.fromInputText(it)
        }

    fun part1(): Int {

        // 'fold' returns the acc value back to the acc param inside the lamda block on each iteration in the loop.
        //  then in the last iteration it returns the final acc value to the 'finalState'
        val finalState = instructions
            .fold(SimpleSubmarineState(0, 0)) { acc, inst ->
                inst.execute(acc)
            }
        return finalState.hor * finalState.dep
    }

    fun part2(): Int {
        val finalState = instructions
            .fold(AimedSubmarineState(0, 0, 0)) { acc, inst ->
                inst.execute(acc)
            }
        return finalState.hor * finalState.dep
    }

    println(part1())
    println(part2())
}