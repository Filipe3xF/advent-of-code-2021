package utils

import arrow.core.Either
import java.io.File

fun readInput(name: String, parent: String = "src/main/resources"): Either<Throwable, List<String>> = Either.catch {
    File(parent, "$name.txt").readLines()
}
