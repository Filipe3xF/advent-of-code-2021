package utils

import arrow.core.Either

class FileDayInputSupplier : DayInputSupplier {
    override fun getDayInput(dayNumber: Int): Either<Throwable, List<String>> =
        readFile("Day$dayNumber", "txt", "src/main/resources/")
}
