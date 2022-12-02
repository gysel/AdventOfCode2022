const val ROCK = 1
const val PAPER = 2
const val SCISSORS = 3

const val LOSE = 'X'
const val DRAW = 'Y'
const val WIN = 'Z'

fun main() {
    val data = InputData.readLines("day02.txt")
    solve("Part 1", 9651) {
        var score = 0
        data.forEach { line ->
            val elf: Int = line[0].code - 64
            val me: Int = line[2].code - 87

            score += me
            if (elf == me) {
                score += 3
            } else if (me == ROCK && elf == SCISSORS) {
                score += 6
            } else if (me == PAPER && elf == ROCK) {
                score += 6
            } else if (me == SCISSORS && elf == PAPER) {
                score += 6
            }
        }
        score
    }

    solve("Part 2", 10560) {

        var score = 0
        data.forEach { line ->
            val elf: Int = line[0].code - 64
            when (line[2]) {
                LOSE -> {
                    when (elf) {
                        ROCK -> {
                            score += SCISSORS
                        }

                        PAPER -> {
                            score += ROCK
                        }

                        SCISSORS -> {
                            score += PAPER
                        }
                    }
                }

                DRAW -> {
                    score += 3 + elf
                }

                WIN -> {
                    score += 6
                    when (elf) {
                        ROCK -> {
                            score += PAPER
                        }

                        PAPER -> {
                            score += SCISSORS
                        }

                        SCISSORS -> {
                            score += ROCK
                        }
                    }

                }

                else -> {
                    throw IllegalStateException()
                }
            }
        }
        score
    }
}