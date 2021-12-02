import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day1Test {

    @Test
    fun `counts the number of increases in a list`() {
        val numberList = listOf(1, 2, 2, 3, 1, 4)

        numberList.countIncreases() shouldBe 3
    }

    @Test
    fun `returns 0 when the list only decreases`() {
        val numberList = (6 downTo 0).toList()

        numberList.countIncreases() shouldBe 0
    }

    @Test
    fun `counts the number of increases in a list with a given window`() {
        val numberList = listOf(1, 2, 3, 1, 1, 1, 3, 2, 1, 1, 1, 1)

        numberList.countIncreasesWindowed(3) shouldBe 2
    }

    @Test
    fun `returns 0 when counting window increases on an even list`() {
        val numberList = generateSequence { 1 }.take(12).toList()

        numberList.countIncreasesWindowed(3) shouldBe 0
    }
}
