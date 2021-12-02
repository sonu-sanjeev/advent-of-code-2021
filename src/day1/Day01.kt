fun main() {

    // Using regular for-loops.
    fun part1(input: List<Int>): Int {
        var inc = 0
        for (i in 1 until input.size) {
            if (input[i] > input[i - 1]) inc++
        }
        return inc
    }

    //Cleaner way.
    fun part1Idiomatic(input: List<Int>) = input
        .windowed(2)
        .count { (a, b) -> b > a }


    // Using regular for-loops.
    fun part2(input: List<Int>): Int {
        var inc = 0
        for (i in 3 until input.size) {
            val sum2 = input[i] + input[i - 1] + input[i - 2]
            val sum1 = input[i - 1] + input[i - 2] + input[i - 3]
            if (sum2 > sum1) inc++
        }
        return inc
    }

    //Cleaner way.
    fun part2Idiomatic(input: List<Int>) = input
        .windowed(3) { it.sum() }
        .windowed(2)
        .count { (a, b) -> b > a }


    val input = readInput("day1\\Day01").map(String::toInt)
    println(part1(input))
    println(part1Idiomatic(input))
    println(part2(input))
    println(part2Idiomatic(input))
}
