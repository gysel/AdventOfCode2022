fun main() {
    val input = InputData.read("day06.txt")

    solve("Part 1", 1109) {
        findMarker(input, 4)
    }
    solve("Part 2", 3965) {
        findMarker(input, 14)
    }
}

private fun findMarker(input: String, markerSize: Int): Int {
    val match = input
        .toCharArray()
        .withIndex()
        .windowed(markerSize)
        .find { indexedValues ->
            // remove duplicates by storing it in a set
            indexedValues.map { it.value }.toSet().size == markerSize
        }
    return match?.last()?.index?.plus(1) ?: -1
}