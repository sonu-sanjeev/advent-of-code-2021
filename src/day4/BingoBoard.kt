package day4

class BingoBoard(private val elements: List<Element>) {

    fun markElement(num: Int): Boolean {
        val element = elements.firstOrNull { it.num == num }
        return element?.let {
            it.isMarked = true
            isCrossed(it)
        } ?: kotlin.run {
            false
        }
    }

    // Returns true if either a row or column has been completely marked true.
    private fun isCrossed(element: Element): Boolean {
        val index = element.pos
        val rowElements = elements.filter { it.pos.first == index.first }
        if (rowElements.all { it.isMarked }) return true
        val columnElements = elements.filter { it.pos.second == index.second }
        if (columnElements.all { it.isMarked }) return true
        return false
    }

    fun getFinalScore(n: Int): Int {
        val unmarkedSum = elements.filter { it.isMarked.not() }.sumOf {
            it.num
        }
        return unmarkedSum * n
    }
}

data class Element(
    val num: Int,
    val pos: Pair<Int, Int>,
    var isMarked: Boolean = false
)