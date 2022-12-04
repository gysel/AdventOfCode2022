fun main() {
    val data: List<List<IntRange>> = InputData
        .readLines("day04.txt")
        .map { line -> line.split(',').map { it.parseRange() } }

    solve("Part 1", 453) {
        data.count { (first, second) ->
            first fullyContains second || second fullyContains first
        }
    }
    solve("Part 2", 919) {
        data.count { (first, second) ->
            first overlapsWith second
        }
    }
}

fun String.parseRange(): IntRange {
    val (first, second) = this.split('-').map { it.toInt() }
    return first..second
}

infix fun IntRange.fullyContains(other: IntRange): Boolean {
    return other.first >= this.first && other.last <= this.last
}

infix fun IntRange.overlapsWith(other: IntRange): Boolean {
    return this.first <= other.last && this.last >= other.first
}
