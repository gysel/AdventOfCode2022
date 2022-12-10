fun main() {
    val indexes = (20..220 step 40)

    solve("Example 1", 13140) {
        calculateSignalStrengths(InputData.readLines("day10-example.txt"), indexes)
    }

    val input = InputData.readLines("day10.txt")
    solve("Part 1", 17840) {
        calculateSignalStrengths(input, indexes)
    }

    solve("Part 2", null) {
        val width = 40
        val registers = calculateRegisters(input)
        val result = (1..240).zip(registers).chunked(width).map { line ->
            line.map { (cycle, register) ->
                val pixel = ((cycle - 1) % width)
                if (pixel == register || pixel == register - 1 || pixel == register + 1) '#' else ' '
            }
        }
        result.map { it.joinToString("") }.forEach(::println)
        "see ASCII art in stdout"
    } // correct: EALGULPG
}

private fun calculateSignalStrengths(input: List<String>, indexes: Iterable<Int>): Int {
    val cycles: List<Int> = calculateRegisters(input)
    return indexes.sumOf { i ->
        cycles[i - 1] * i
    }
}

private fun calculateRegisters(input: List<String>): List<Int> {
    var register = 1
    return listOf(register) + input.flatMap { line ->
        when (line.take(4)) {
            "addx" -> {
                val n = line.drop(5).toInt()
                listOf(register, register + n).also { (_, newRegister) ->
                    register = newRegister
                }
            }

            "noop" -> listOf(register)
            else -> throw IllegalStateException()
        }
    }
}
