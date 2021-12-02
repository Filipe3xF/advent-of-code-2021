package utils

import arrow.core.Either
import java.io.FileNotFoundException

class Day<T, R, V>(
    private val number: Int,
    private val inputTransformation: (List<String>) -> T,
    private val partOne: (T) -> R,
    private val partTwo: (T) -> V,
    dayInputSupplier: DayInputSupplier = HttpDayInputSupplier()
) {

    private val input: Either<Throwable, T> = dayInputSupplier.getDayInput(number).map { inputTransformation(it) }

    fun printResult() = input.fold({ handleThrowable(it) }) { handleInput(it) }

    private fun handleThrowable(throwable: Throwable) {
        print("It was not possible to get the result of the Day $number:\n")
        when (throwable) {
            is FileNotFoundException -> println("There is no file for Day $number.")
            is HttpDayInputSupplier.HttpDayInputRequestError -> println(throwable.message)
            else -> throw throwable
        }
    }

    private fun handleInput(input: T) = println(
        """
            Results for Day $number:
                Part 1: ${partOne(input)}
                Part 2: ${partTwo(input)}
        """.trimIndent()
    )
}
