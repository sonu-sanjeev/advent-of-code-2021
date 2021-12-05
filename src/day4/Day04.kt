package day4

import readInput

val input = readInput("Day4/Day04")

val bingoInputs = input.first().split(",").map {
    it.toInt()
}

val boards = input.drop(1).chunked(6) {
    it.drop(1).map { line ->
        line.split(" ")
            .filterNot { num ->
                num.isBlank()
            }
    }
}

val bingoBoards = boards.map { board ->
    board.mapIndexed { columnIndex, rowElements ->
        rowElements.mapIndexed { rowIndex, element ->
            Element(element.toInt(), Pair(rowIndex, columnIndex))
        }
    }.flatten()
}.map {
    BingoBoard(it)
}

// Finding the first winning board.
fun part1() {
    bingoInputs.forEach { number ->
        bingoBoards.firstOrNull {
            it.markElement(number)
        }?.let {
            println(it.getFinalScore(number))
            return
        }
    }
}

// Finding the last winning board.
fun part2() {
    val result = findLastWinningBoard(0, bingoBoards)
    println(result.second.getFinalScore(result.first))
}

fun findLastWinningBoard(index: Int, bingoBoards: List<BingoBoard>): Pair<Int, BingoBoard> {
    val bingoInput = bingoInputs[index]
    if (bingoInputs.last() == bingoInput || bingoBoards.size == 1) {
        val lastWinningBoard = bingoBoards.first().apply {
            markElement(bingoInput)
        }
        return Pair(bingoInput, lastWinningBoard)
    }

    return findLastWinningBoard(index + 1, bingoBoards.filterNot { it.markElement(bingoInputs[index]) })
}

fun main() {
    part1()
    part2()
}