import utils.Day

fun main() = Day(3, { it },
    { calculateSubmarinePowerConsumption(it) },
    { calculateSubmarineLifeSupportRating(it) }
).printResult()

fun calculateSubmarinePowerConsumption(diagnosticReport: List<String>): Int {
    val gammaRate = diagnosticReport.stringWithMostCommonCharacters()
    val epsilonRate = gammaRate.invert()

    return gammaRate * epsilonRate
}

fun calculateSubmarineLifeSupportRating(diagnosticReport: List<String>): Int {
    val oxygenGeneratorRating = diagnosticReport.getStringWithRunningPredicate {
        getMostCommonCharacterAtIndex(it)
    }
    val co2ScrubberRating = diagnosticReport.getStringWithRunningPredicate {
        getLeastCommonCharacterAtIndex(it, '0')
    }

    return oxygenGeneratorRating * co2ScrubberRating
}

fun List<String>.stringWithMostCommonCharacters(): String =
    first().indices.map { getMostCommonCharacterAtIndex(it) }.joinToString("")

fun List<String>.getStringWithRunningPredicate(predicate: List<String>.(Int) -> Char): String {
    var list = this

    for (index in first().indices) {
        val characterMatchingPredicate = list.predicate(index)
        list = list.filter { it[index] == characterMatchingPredicate }
        if (list.size == 1) break
    }

    return list.single()
}

fun List<String>.getMostCommonCharacterAtIndex(index: Int, ifEqual: Char = '1'): Char =
    getCountOfCharacterOccurrencesAtIndex(index).also { println(it) }
        .toSortedMap(prioritizeCharacter(ifEqual))
        .maxByOrNull { it.value }!!.key

fun List<String>.getLeastCommonCharacterAtIndex(index: Int, ifEqual: Char = '1'): Char =
    getCountOfCharacterOccurrencesAtIndex(index)
        .toSortedMap(prioritizeCharacter(ifEqual))
        .minByOrNull { it.value }!!.key

private fun prioritizeCharacter(character: Char) = { first: Char, second: Char ->
    if (first == character) -1
    else if (second == character) 1
    else 0
}

fun List<String>.getCountOfCharacterOccurrencesAtIndex(index: Int): Map<Char, Int> =
    groupingBy { it[index] }.eachCount()

fun String.invert(): String = map {
    if (it == '1') '0'
    else '1'
}.joinToString("")

operator fun String.times(other: String) = toDecimal() * other.toDecimal()

fun String.toDecimal() = toInt(2)
