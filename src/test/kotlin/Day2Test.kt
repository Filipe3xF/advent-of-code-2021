import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldBeSameSizeAs
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day2Test {

    @Test
    fun `extracts instructions from input`() {
        val inputList = listOf("forward 1", "down 2", "up 3")
        val instructions = extractInstructions(inputList)

        assertSoftly {
            instructions shouldBeSameSizeAs inputList
            instructions[0] shouldBe ("forward" to 1)
            instructions[1] shouldBe ("down" to 2)
            instructions[2] shouldBe ("up" to 3)
        }
    }

    @Test
    fun `SimpleSubmarinePosition follows instructions accordingly`() {
        val instructions = listOf(
            "forward" to 5,
            "down" to 5,
            "forward" to 8,
            "up" to 3,
            "down" to 8,
            "forward" to 2
        )
        val initialPosition = 0 to 0
        val finalPosition = initialPosition.followInstructions(instructions)

        finalPosition shouldBe (15 to 10)
    }

    @Test
    fun `SimpleSubmarinePosition follows instruction accordingly`() {
        val instruction = "forward" to 5
        val initialPosition = 0 to 0
        val finalPosition = initialPosition.followInstruction(instruction)

        finalPosition shouldBe (5 to 0)
    }

    @Test
    fun `SimpleSubmarinePosition ignores invalid instructions`() {
        val instructions = listOf(
            "nonValidCommand" to -1,
            "nonValidCommand" to 3,
            "nonValidCommand" to 4
        )
        val initialPosition = 0 to 0
        val finalPosition = initialPosition.followInstructions(instructions)

        finalPosition shouldBe initialPosition
    }

    @Test
    fun `SimpleSubmarinePosition ignores invalid instruction`() {
        val instruction = "nonValidCommand" to 3
        val initialPosition = 0 to 0
        val finalPosition = initialPosition.followInstruction(instruction)

        finalPosition shouldBe initialPosition
    }

    @Test
    fun `multiplies members of a SimpleSubmarinePosition`() {
        val submarinePosition = 15 to 10

        submarinePosition.multiply() shouldBe 150
    }

    @Test
    fun `adds depth to a SimpleSubmarinePosition`() {
        val submarinePosition = 0 to 0

        submarinePosition.addDepth(1) shouldBe (0 to 1)
    }

    @Test
    fun `subtracts depth to a SimpleSubmarinePosition`() {
        val submarinePosition = 0 to 0

        submarinePosition.subtractDepth(1) shouldBe (0 to -1)
    }

    @Test
    fun `adds horizontal to a SimpleSubmarinePosition`() {
        val submarinePosition = 0 to 0

        submarinePosition.addHorizontal(1) shouldBe (1 to 0)
    }

    @Test
    fun `SubmarinePosition follows instructions accordingly`() {
        val instructions = listOf(
            "forward" to 5,
            "down" to 5,
            "forward" to 8,
            "up" to 3,
            "down" to 8,
            "forward" to 2
        )
        val initialPosition = SubmarinePosition(0, 0, 0)
        val finalPosition = initialPosition.followInstructions(instructions)

        finalPosition shouldBe SubmarinePosition(15, 60, 10)
    }

    @Test
    fun `SubmarinePosition follows instruction accordingly`() {
        val instruction = "forward" to 5
        val initialPosition = SubmarinePosition(1, 2, 3)
        val finalPosition = initialPosition.followInstruction(instruction)

        finalPosition shouldBe SubmarinePosition(6, 17, 3)
    }

    @Test
    fun `SubmarinePosition ignores invalid instructions`() {
        val instructions = listOf(
            "nonValidCommand" to -1,
            "nonValidCommand" to 3,
            "nonValidCommand" to 4
        )
        val initialPosition = SubmarinePosition(0, 0, 0)
        val finalPosition = initialPosition.followInstructions(instructions)

        finalPosition shouldBe initialPosition
    }

    @Test
    fun `SubmarinePosition ignores invalid instruction`() {
        val instruction = "nonValidCommand" to 3
        val initialPosition = SubmarinePosition(0, 0, 0)
        val finalPosition = initialPosition.followInstruction(instruction)

        finalPosition shouldBe initialPosition
    }

    @Test
    fun `multiplies members of a SubmarinePosition`() {
        val submarinePosition = SubmarinePosition(1, 2, 3)

        submarinePosition.multiply() shouldBe 2
    }

    @Test
    fun `adds aim to a SubmarinePosition`() {
        val submarinePosition = SubmarinePosition(1, 2, 3)

        submarinePosition.addAim(1) shouldBe SubmarinePosition(1, 2, 4)
    }

    @Test
    fun `subtracts aim to a SubmarinePosition`() {
        val submarinePosition = SubmarinePosition(1, 2, 3)

        submarinePosition.subtractAim(1) shouldBe SubmarinePosition(1, 2, 2)
    }

    @Test
    fun `adds horizontal to a SubmarinePosition`() {
        val submarinePosition = SubmarinePosition(1, 2, 3)

        submarinePosition.addHorizontal(1) shouldBe SubmarinePosition(2, 2, 3)
    }

    @Test
    fun `adds depth to a SubmarinePosition`() {
        val submarinePosition = SubmarinePosition(1, 2, 3)

        submarinePosition.addDepth(2) shouldBe SubmarinePosition(1, 8, 3)
    }
}
