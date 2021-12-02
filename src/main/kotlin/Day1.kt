import utils.Day

fun main() = Day(
    1,
    { list -> list.map { it.toInt() } },
    { it.countIncreases() },
    { it.countIncreasesWindowed(3) },
).printResult()

fun List<Int>.countIncreases(): Int {
    var count = 0
    for (index in 1 until size) {
        if (get(index) > get(index - 1)) count++
    }
    return count
}

fun List<Int>.countIncreasesWindowed(windowSize: Int): Int = windowed(windowSize).map { it.sum() }.countIncreases()
