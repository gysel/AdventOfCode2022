fun main() {

//    solve("Small Example", 1 + 3 * 4 + 5 * -1) {
//        val input = """
//            noop
//            addx 3
//            addx -5
//        """.trimIndent().lines()
//        calculateSignalStrengths(input, listOf(1, 3, 5))
//    }

    solve("Example 1", 13140) {
        val indexes = listOf(20, 60, 100, 140, 180, 220)
        calculateSignalStrengths(InputData.readLines("day10-example.txt"), indexes)
    }


    val input = InputData.readLines("day10.txt")
    solve("Part 1", 17840) {
        val indexes = listOf(20, 60, 100, 140, 180, 220)
        calculateSignalStrengths(input, indexes)
    }

    solve("Part 2", null) {
        val registers = calculateRegisters(input)
        (1..240).zip(registers).chunked(40).map { line ->
            val rendered = line.map { (cycle, register) ->
                val x = cycle % 40
                if (x == register || x == register - 1 || x == register + 1) '#' else ' '
            }
            println(rendered.joinToString(""))
        }
        "see ASCII art in stdout"
    } // correct: EALGULPG
}

private fun calculateSignalStrengths(input: List<String>, indexes: List<Int>): Int {
    val cycles: List<Int> = calculateRegisters(input)
    return indexes.sumOf { i ->
        cycles[i - 2] * i
    }
}

private fun calculateRegisters(input: List<String>): List<Int> {
    var register = 1
    val cycles: List<Int> = input.flatMap { line ->
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
    return cycles
}
