import utils.Day

fun main() = Day(2, ::extractInstructions,
    { (0 to 0).followInstructions(it).multiply() },
    { SubmarinePosition(0, 0, 0).followInstructions(it).multiply() }
).printResult()


fun extractInstructions(inputList: List<String>) =
    inputList.map { it.split(" ") }.map { (command, delta) -> command to delta.toInt() }


typealias SimpleSubmarinePosition = Pair<Int, Int>

fun SimpleSubmarinePosition.followInstructions(instructions: List<Pair<String, Int>>): SimpleSubmarinePosition =
    instructions.fold(this) { submarinePosition, instruction -> submarinePosition.followInstruction(instruction) }

fun SimpleSubmarinePosition.followInstruction(instruction: Pair<String, Int>): SimpleSubmarinePosition {
    val (command, delta) = instruction
    return when (command) {
        "up" -> subtractDepth(delta)
        "down" -> addDepth(delta)
        "forward" -> addHorizontal(delta)
        else -> this
    }
}

fun SimpleSubmarinePosition.multiply() = first * second
fun SimpleSubmarinePosition.addDepth(toAdd: Int) = first to second.plus(toAdd)
fun SimpleSubmarinePosition.subtractDepth(toSubtract: Int) = first to second.minus(toSubtract)
fun SimpleSubmarinePosition.addHorizontal(toAdd: Int) = first.plus(toAdd) to second


typealias SubmarinePosition = Triple<Int, Int, Int>

fun SubmarinePosition.followInstructions(instructions: List<Pair<String, Int>>): SubmarinePosition =
    instructions.fold(this) { submarinePosition, instruction -> submarinePosition.followInstruction(instruction) }

fun SubmarinePosition.followInstruction(instruction: Pair<String, Int>): SubmarinePosition {
    val (command, delta) = instruction
    return when (command) {
        "up" -> subtractAim(delta)
        "down" -> addAim(delta)
        "forward" -> addHorizontal(delta).addDepth(delta)
        else -> this
    }
}

fun SubmarinePosition.multiply() = first * second
fun SubmarinePosition.addAim(toAdd: Int) = SubmarinePosition(first, second, third.plus(toAdd))
fun SubmarinePosition.subtractAim(toSubtract: Int) = SubmarinePosition(first, second, third.minus(toSubtract))
fun SubmarinePosition.addHorizontal(toAdd: Int) = SubmarinePosition(first.plus(toAdd), second, third)
fun SubmarinePosition.addDepth(toAdd: Int) = SubmarinePosition(first, second.plus(third * toAdd), third)
