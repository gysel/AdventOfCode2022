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

    solve("Example part 1", 3) {
        val file: List<Number> = exampleInput.map(String::toLong).map { Number(it) }
        val result = mix(file)
        calculateGroveCoordinates(result, file)
    }

    solve("Part 1", 4066) {
        val file: List<Number> = input.map(String::toLong).map { Number(it) }
        val result = mix(file)
        calculateGroveCoordinates(result, file)
    }

    solve("Example part 2", 1_623_178_306) {
        val decryptionKey = 811_589_153
        val file: List<Number> = exampleInput.map(String::toLong).map { it * decryptionKey }.map { Number(it) }
        val result = mix(file, 10)
        calculateGroveCoordinates(result, file)
    }

    solve("Part 2", null) {
        val decryptionKey = 811_589_153
        val file: List<Number> = input.map(String::toLong).map { it * decryptionKey }.map { Number(it) }
        val result = mix(file, 10)
        calculateGroveCoordinates(result, file)

    }
}

private fun mix(file: List<Number>, times: Int = 1): List<Number> {
    val mixing = file.toMutableList()
    repeat(times) { round ->
        println("mixing round $round")
        file.forEach { number ->
            val index = mixing.indexOf(number)
            var newIndex = (index + number.n)
            while (newIndex <= 0) {
                newIndex += file.size - 1
            }
            while (newIndex >= file.size) {
                newIndex += -(file.size - 1)
            }
            //println("${number.n} moves from $index to $newIndex")
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
