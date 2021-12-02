package utils

import arrow.core.Either
import java.io.FileNotFoundException

class Day<T, R, V>(
    private val number: Int,
    private val inputTransformation: (List<String>) -> T,
    private val partOne: (T) -> R,
    private val partTwo: (T) -> V,
) {

    private val input: Either<Throwable, T> = readInput("Day$number").map { inputTransformation(it) }

    fun printResult() {
        input.fold({
            print("It was not possible to get the result of the Day $number:")
            when (it) {
                is FileNotFoundException -> println("\nThere is no file for Day $number.")
                else -> throw it
            }
        }) {
            println(
                """
                    Results for Day $number:
                        Part 1: ${partOne(it)}
                        Part 2: ${partTwo(it)}
                """.trimIndent()
            )
        }
    }
}
