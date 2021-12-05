import BingoBoard.Companion.doesAnyLineHaveBingo
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day4Test {

    private val input = listOf(
        "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1",
        "\n",
        "22 13 17 11  0",
        " 8  2 23  4 24",
        "21  9 14 16  7",
        " 6 10  3 18  5",
        " 1 12 20 15 19",
        "\n",
        " 3 15  0  2 22",
        " 9 18 13 17  5",
        "19  8  7 25 23",
        "20 11 10 24  4",
        "14 21 16 12  6",
        "\n",
        "14 21 17 24  4",
        "10 16 15  9 19",
        "18  8 23 26 20",
        "22 11 13  6  5",
        " 2  0 12  3  7",
    )

    @Test
    fun `extracts bingo numbers from input`() {
        val bingoNumbers = extractBingoNumbers(input)

        assertSoftly {
            bingoNumbers shouldHaveSize 27

            bingoNumbers.first() shouldBe 7
            bingoNumbers.last() shouldBe 1
        }
    }

    @Test
    fun `extracts bingo boards from input`() {
        val bingoBoards = extractBingoBoards(input)

        val lastBoard = bingoBoards.last()
        val firstLineOfLastBoard = lastBoard.lines.first()

        assertSoftly {
            bingoBoards shouldHaveSize 3

            lastBoard.lines shouldHaveSize 5

            firstLineOfLastBoard shouldHaveSize 5
            firstLineOfLastBoard.first().value shouldBe 14
            firstLineOfLastBoard.last().value shouldBe 4
        }
    }

    @Test
    fun `extracts bingo from input`() {
        val bingo = extractBingo(input)

        assertSoftly {
            bingo.numbers shouldHaveSize 27
            bingo.boards shouldHaveSize 3
        }
    }

    @Test
    fun `marks a number on a bingo board`() {
        val bingoBoard = extractBingoBoards(input).first()

        bingoBoard.mark(14)
        bingoBoard.mark(22)

        val countOfMarkedNumbers = bingoBoard.lines.sumOf { it.count { number -> number.marked } }
        val markedNumbers = bingoBoard.lines.flatMap { it.filter { number -> number.marked } }.map { it.value }

        assertSoftly {
            countOfMarkedNumbers shouldBe 2
            markedNumbers shouldContainExactlyInAnyOrder listOf(14, 22)
        }
    }

    @Test
    fun `transposes board's lines`() {
        val boardLines = extractBingoBoards(input).first().lines
        val transposedBoardLines = boardLines.transpose()

        assertSoftly {
            transposedBoardLines[0][0] shouldBe boardLines[0][0]
            transposedBoardLines[0][1] shouldBe boardLines[1][0]
            transposedBoardLines[0][2] shouldBe boardLines[2][0]
            transposedBoardLines[0][3] shouldBe boardLines[3][0]
            transposedBoardLines[0][4] shouldBe boardLines[4][0]
        }
    }

    @Test
    fun `detects when any line has bingo`() {
        val lines = listOf(
            listOf(
                BingoBoardNumber(1),
                BingoBoardNumber(2)
            ),
            listOf(
                BingoBoardNumber(3, true),
                BingoBoardNumber(4, true)
            )
        )

        lines.doesAnyLineHaveBingo() shouldBe true
    }

    @Test
    fun `returns false when no line has bingo`() {
        val lines = listOf(
            listOf(
                BingoBoardNumber(1),
                BingoBoardNumber(2)
            ),
            listOf(
                BingoBoardNumber(3),
                BingoBoardNumber(4)
            )
        )

        lines.doesAnyLineHaveBingo() shouldBe false
    }

    @Test
    fun `detects when any line of a board has bingo`() {
        val lines = listOf(
            listOf(
                BingoBoardNumber(1),
                BingoBoardNumber(2)
            ),
            listOf(
                BingoBoardNumber(3, true),
                BingoBoardNumber(4, true)
            )
        )

        val board = BingoBoard(lines)

        board.hasBingoInLines() shouldBe true
    }

    @Test
    fun `returns false when no line of a board has bingo`() {
        val lines = listOf(
            listOf(
                BingoBoardNumber(1),
                BingoBoardNumber(2, true)
            ),
            listOf(
                BingoBoardNumber(3),
                BingoBoardNumber(4, true)
            )
        )

        val board = BingoBoard(lines)

        board.hasBingoInLines() shouldBe false
    }

    @Test
    fun `detects when any column of a board has bingo`() {
        val lines = listOf(
            listOf(
                BingoBoardNumber(1),
                BingoBoardNumber(2, true)
            ),
            listOf(
                BingoBoardNumber(3),
                BingoBoardNumber(4, true)
            )
        )

        val board = BingoBoard(lines)

        board.hasBingoInColumns() shouldBe true
    }

    @Test
    fun `returns false when no column of a board has bingo`() {
        val lines = listOf(
            listOf(
                BingoBoardNumber(1, true),
                BingoBoardNumber(2, true)
            ),
            listOf(
                BingoBoardNumber(3),
                BingoBoardNumber(4)
            )
        )

        val board = BingoBoard(lines)

        board.hasBingoInColumns() shouldBe false
    }

    @Test
    fun `detects when board has bingo`() {
        val lines = listOf(
            listOf(
                BingoBoardNumber(1),
                BingoBoardNumber(2, true)
            ),
            listOf(
                BingoBoardNumber(3),
                BingoBoardNumber(4, true)
            )
        )

        val board = BingoBoard(lines)

        board.hasBingo() shouldBe true
    }

    @Test
    fun `returns false when board does not have bingo`() {
        val lines = listOf(
            listOf(
                BingoBoardNumber(1, true),
                BingoBoardNumber(2)
            ),
            listOf(
                BingoBoardNumber(3),
                BingoBoardNumber(4, true)
            )
        )

        val board = BingoBoard(lines)

        board.hasBingoInColumns() shouldBe false
    }

    @Test
    fun `finds which board wins`() {
        val bingo = extractBingo(input)
        val (_, winnerBoard) = bingo.play()

        winnerBoard shouldBe bingo.boards[2]
    }

    @Test
    fun `calculates score of winner board`() {
        val bingo = extractBingo(input)
        val (lastNumberCalled, winnerBoard) = bingo.play()

        winnerBoard.calculateScore(lastNumberCalled) shouldBe 4512
    }

    @Test
    fun `finds which board wins last`() {
        val bingo = extractBingo(input)
        val (_, winnerBoard) = bingo.playLastManStanding()

        winnerBoard shouldBe bingo.boards[1]
    }

    @Test
    fun `calculates score of last board to win`() {
        val bingo = extractBingo(input)
        val (lastNumberCalled, winnerBoard) = bingo.playLastManStanding()

        winnerBoard.calculateScore(lastNumberCalled) shouldBe 1924
    }
}
