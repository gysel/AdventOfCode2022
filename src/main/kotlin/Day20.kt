fun main() {
    val exampleInput = """
        1
        2
        -3
        3
        -2
        0
        4
    """.trimIndent().lines()
    val input = InputData.readLines("day20.txt")
    val decryptionKey = 811_589_153

    solve("Example part 1", 3) {
        val file: List<Number> = exampleInput.parseFile()
        val result = file.mix()
        calculateGroveCoordinates(result, file)
    }

    solve("Part 1", 4066) {
        val file: List<Number> = input.parseFile()
        val result = file.mix()
        calculateGroveCoordinates(result, file)
    }

    solve("Example part 2", 1_623_178_306) {
        val file: List<Number> = exampleInput.parseFile(decryptionKey)
        val result = file.mix(10)
        calculateGroveCoordinates(result, file)
    }

    solve("Part 2", 6_704_537_992_933) {
        val file: List<Number> = input.parseFile(decryptionKey)
        val result = file.mix(10)
        calculateGroveCoordinates(result, file)
    }
}

private fun List<String>.parseFile(decryptionKey: Int = 1) =
    map { Number(it.toLong() * decryptionKey) }

private fun List<Number>.mix(times: Int = 1): List<Number> {
    val mixing = toMutableList()
    repeat(times) {
        forEach { number ->
            val index = mixing.indexOf(number)
            var newIndex = (index + number.n)
            newIndex %= (size - 1)
            if (newIndex <= 0) {
                newIndex += size - 1
            }
            mixing.removeAt(index)
            mixing.add(newIndex.toInt(), number)
        }
    }
    return mixing.toList()
}

private fun calculateGroveCoordinates(numbers: List<Number>, file: List<Number>): Long {
    val index = numbers.indexOf(file.first { it.n == 0L })
    val result = listOf(1000, 2000, 3000).map { i ->
        numbers[(index + i) % file.size].n
    }
    return result.sum()
}

class Number(val n: Long) {
    override fun toString(): String {
        return n.toString()
    }
}
