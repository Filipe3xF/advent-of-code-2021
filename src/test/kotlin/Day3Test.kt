import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day3Test {

    private val inputList = listOf(
        "00100",
        "11110",
        "10110",
        "10111",
        "10101",
        "01111",
        "00111",
        "11100",
        "10000",
        "11001",
        "00010",
        "01010",
    )

    @Test
    fun `converts a binary string to decimal`() {
        val binaryString = "01001"
        val decimal = binaryString.toDecimal()

        decimal shouldBe 9
    }

    @Test
    fun `multiplies a binary string with another and gets decimal value`() {
        val binaryString = "01001"
        val otherBinaryString = "10110"

        val result = binaryString * otherBinaryString

        result shouldBe 198
    }

    @Test
    fun `inverts a binary string`() {
        val binaryString = "10110"
        val invertedBinaryString = binaryString.invert()

        invertedBinaryString shouldBe "01001"
    }

    @Test
    fun `gets the most common character in a specific position of each string`() {
        assertSoftly {
            inputList.getMostCommonCharacterAtIndex(0) shouldBe '1'
            inputList.getMostCommonCharacterAtIndex(1) shouldBe '0'
        }

    }

    @Test
    fun `gets the least common character in a specific position of each string`() {
        assertSoftly {
            inputList.getLeastCommonCharacterAtIndex(2) shouldBe '0'
            inputList.getLeastCommonCharacterAtIndex(3) shouldBe '0'
        }
    }

    @Test
    fun `gets given character if characters in a position are equally common`() {
        val list = listOf(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
        )

        assertSoftly {
            list.getMostCommonCharacterAtIndex(4, '1') shouldBe '1'
            list.getMostCommonCharacterAtIndex(4, '0') shouldBe '0'
            list.getLeastCommonCharacterAtIndex(4, '1') shouldBe '1'
            list.getLeastCommonCharacterAtIndex(4, '0') shouldBe '0'
        }
    }

    @Test
    fun `forms a string out of the most common characters in each position of a list of strings`() {
        inputList.stringWithMostCommonCharacters() shouldBe "10110"
    }

    @Test
    fun `calculates the power consumption of the submarine`() {
        val powerConsumption = calculateSubmarinePowerConsumption(inputList)
        powerConsumption shouldBe 198
    }

    @Test
    fun `gets last string of a list that its characters matches a given predicate`() {
        val first = inputList.getStringWithRunningPredicate {
            getMostCommonCharacterAtIndex(it)
        }

        val second = inputList.getStringWithRunningPredicate {
            getLeastCommonCharacterAtIndex(it, '0')
        }

        assertSoftly {
            first shouldBe "10111"
            second shouldBe "01010"
        }
    }

    @Test
    fun `calculates the life support rating of the submarine`() {
        val lifeSupportRating = calculateSubmarineLifeSupportRating(inputList)
        lifeSupportRating shouldBe 230
    }
}
