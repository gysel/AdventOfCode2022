fun main() {
    val data: List<List<IntRange>> = InputData
        .readLines("day04.txt")
        .map { line -> line.split(',').map { it.parseRange() } }

    solve("Part 1", 453) {
        data.count { (first, second) ->
            first fullyContains second || second fullyContains first
        }
    }
    solve("Part 2", null) {
        data.count { (first, second) ->
            first overlap second
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

infix fun IntRange.overlap(other: IntRange): Boolean {
    // this can certainly be implemented way faster - but the result is correct so ...
    return (this.toSet() intersect other.toSet()).isNotEmpty()
}
