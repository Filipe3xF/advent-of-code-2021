import utils.Day

fun main() = Day(
    1,
    { list -> list.map { it.toInt() } },
    { it.countIncreases() },
    { it.countIncreasesWindowed(3) },
).printResult()

fun List<Int>.countIncreases(): Int = zipWithNext().count { it.second > it.first }

fun List<Int>.countIncreasesWindowed(windowSize: Int): Int = windowed(windowSize) { it.sum() }.countIncreases()
