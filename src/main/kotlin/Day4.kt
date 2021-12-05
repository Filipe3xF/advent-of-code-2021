import utils.Day

fun main() = Day(4, ::extractBingo,
    {
        it.play().let { (lastNumberCalled, winningBoard) -> winningBoard.calculateScore(lastNumberCalled) }
    },
    {
        it.playLastManStanding()
            .let { (lastNumberCalled, winningBoard) -> winningBoard.calculateScore(lastNumberCalled) }
    }
).printResult()

fun extractBingo(input: List<String>): Bingo {
    val bingoNumbers = extractBingoNumbers(input)
    val bingoBoards = extractBingoBoards(input)

    return Bingo(bingoNumbers, bingoBoards)
}

fun extractBingoNumbers(input: List<String>): List<Int> = input[0].split(",").map { it.toInt() }

fun extractBingoBoards(input: List<String>): List<BingoBoard> = input.asSequence()
    .drop(1)
    .filter { it.isNotBlank() }
    .map { line -> line.split(" ").filter { it.isNotBlank() }.map { BingoBoardNumber(it.toInt()) } }
    .chunked(5)
    .map { BingoBoard(it) }.toList()

data class Bingo(val numbers: List<Int>, val boards: List<BingoBoard>) {

    fun play(): Pair<Int, BingoBoard> {
        numbers.forEach { number ->
            boards.forEach { it.mark(number) }
            boards.find { it.hasBingo() }?.let {
                return number to it
            }
        }
        return numbers.last() to boards.last()
    }

    fun playLastManStanding(): Pair<Int, BingoBoard> {
        val playingBoards: MutableList<BingoBoard> = boards.toMutableList()
        numbers.forEach { number ->
            playingBoards.forEach { it.mark(number) }

            val boardsWithBingo = playingBoards.filter { it.hasBingo() }
            playingBoards.removeAll(boardsWithBingo)

            if (playingBoards.isEmpty())
                return number to boardsWithBingo.last()
        }
        return numbers.last() to boards.last()
    }
}

data class BingoBoard(val lines: List<List<BingoBoardNumber>>) {

    fun mark(number: Int) {
        lines.forEach { line ->
            line.filter { it.value == number }.onEach { it.marked = true }
        }
    }

    fun hasBingo(): Boolean = hasBingoInLines() || hasBingoInColumns()

    fun hasBingoInLines(): Boolean = lines.doesAnyLineHaveBingo()

    fun hasBingoInColumns(): Boolean = lines.transpose().doesAnyLineHaveBingo()

    fun calculateScore(number: Int): Int =
        if (hasBingo())
            lines.sumOf { line -> line.filter { !it.marked }.sumOf { it.value } } * number
        else 0

    companion object {
        fun List<List<BingoBoardNumber>>.doesAnyLineHaveBingo() = any { line -> line.all { it.marked } }
    }
}

data class BingoBoardNumber(val value: Int, var marked: Boolean = false)

fun <T> List<List<T>>.transpose(): List<List<T>> {
    require(all { it.size == first().size })
    return first().indices.map { index -> map { it[index] } }
}
