package utils

import arrow.core.Either
import java.io.File

fun readFile(
    name: String,
    extension: String,
    parent: String
): Either<Throwable, List<String>> = Either.catch {
    File(parent, "$name.$extension").readLines()
}
