import java.util.*

fun main() {
    val input = InputData
        .readLines("day05.txt")

    solve("Part 1", "QNHWJVJZW") {
        val stacks = parseStacks(input)
        val instructions = input
            .drop(10)
            .flatMap { line ->
                val (_, amount, _, from, _, to) = line.split(' ')
                List(amount.toInt()) { listOf(1, from.toInt(), to.toInt()) }
            }
        operateCrane(instructions, stacks)
        stacks.tops()
    }

    solve("Part 2", "BPCZJLFJW") {
        val stacks = parseStacks(input)
        val instructions = input
            .drop(10)
            .map { line ->
                val (_, amount, _, from, _, to) = line.split(' ')
                listOf(amount.toInt(), from.toInt(), to.toInt())
            }
        operateCrane(instructions, stacks)
        stacks.tops()
    }
}


fun parseStacks(input: List<String>): List<Stack<Char>> {
    val stackInput = input
        .take(8)
    return (1..9).map { stackNr ->
        (8 downTo 1)
            .map { row ->
                val charIndex = (stackNr - 1) * 4 + 1
                try {
                    stackInput[row - 1][charIndex]
                } catch (e: StringIndexOutOfBoundsException) {
                    ' '
                }
            }
            .filter { it != ' ' }
            .let {
                val stack = Stack<Char>()
                it.forEach { stack.push(it) }
                stack
            }
    }
}

/**
 * Compatible with CrateMover 9000 as well as CrateMover 9001 - as long as you only
 * pass instructions with an amount of 1 for the CrateMover 9000.
 */
fun operateCrane(
    instructions: List<List<Int>>,
    stacks: List<Stack<Char>>
) {
    instructions.forEach { (amount, from, to) ->
        val temp = Stack<Char>()
        repeat(amount) {
            temp.push(stacks[from - 1].pop())
        }
        repeat(amount) {
            stacks[to - 1].push(temp.pop())
        }
    }
}

private operator fun <E> List<E>.component6(): E {
    return this[5]
}

fun List<Stack<Char>>.tops() = map { it.peek() }.joinToString("")
