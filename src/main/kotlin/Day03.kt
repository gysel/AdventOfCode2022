fun main() {
    val data = InputData.readLines("day03.txt")

    solve("Part 1", 7850) {
        data
            .map {
                val compartmentSize = it.length / 2
                it.take(compartmentSize).toSet() to it.drop(compartmentSize).toSet()
            }
            .sumOf { (first, second) ->
                val commonItems = first intersect second
                if (commonItems.size > 1) throw IllegalStateException()
                calculatePriority(commonItems.first())
            }
    }
    solve("Part 2", 2581) {
        data
            .map { it.toSet() }
            .chunked(3)
            .sumOf { (firstGroup, secondGroup, thirdGroup) ->
                val badge = firstGroup intersect secondGroup intersect thirdGroup
                if (badge.size != 1) throw IllegalStateException()
                calculatePriority(badge.first())
            }
    }
}

private fun calculatePriority(item: Char) = when (item) {
    in 'a'..'z' -> {
        item.code - 96
    }

    in 'A'..'Z' -> {
        item.code - 38
    }

    else -> throw IllegalStateException()
}