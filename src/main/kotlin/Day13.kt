fun main() {
    solve("Example", 13) {
        val input = """
            [1,1,3,1,1]
            [1,1,5,1,1]
            
            [[1],[2,3,4]]
            [[1],4]
            
            [9]
            [[8,7,6]]
            
            [[4,4],4,4]
            [[4,4],4,4,4]
            
            [7,7,7,7]
            [7,7,7]
            
            []
            [3]
            
            [[[]]]
            [[]]
            
            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
            """.trimIndent()

        val correctPairs = input.split("\n\n")
            .map { it.parsePair() }
            .withIndex()
            .filter { it.value.inCorrectOrder() }
            .map { it.index + 1 }

        // should be 1, 2, 4, and 6
        println("Correct: $correctPairs")

        correctPairs.sum()
    }

    solve("Part 1", null) {
        val input = InputData.read("day13.txt")

    }
    solve("Part 2", null) {

    }
}

private fun Pair<Packet, Packet>.inCorrectOrder(): Boolean {
    var index = 0
    while (index < first.data.size && index < second.data.size) {
        val left = first.data[index]
        val right = second.data[index]
        // If the left list runs out of items first, the inputs are in the right order.
        if (left == null) {
            return true
        }
        // If the right list runs out of items first, the inputs are not in the right order.
        else if (right == null) {
            return false
        } else if (left.number < right.number) {
            return true
        } else if (left.number > right.number) {
            return false
        }
        index++
    }
    return true
}

private fun String.parsePair(): Pair<Packet, Packet> {
    val (firstLine, secondLine) = this.lines()
    return Packet.parse(firstLine) to Packet.parse(secondLine)
}

data class Packet(
    val data: List<Value>
) {
    companion object {
        fun parse(line: String): Packet {
            var toProcess = line.drop(1)
            var depth = 1
            val data = mutableListOf<Value>()
            while (toProcess.isNotEmpty()) {
                val chunk = toProcess.takeWhile { it !in setOf('[', ',', ']') }
                if (chunk.isNotEmpty()) {
                    data.add(Value(chunk.toInt(), depth))
                    toProcess = toProcess.drop(chunk.length)
                } else {
                    when (toProcess.take(1)) {
                        "[" -> depth++
                        "]" -> depth--
                        "," -> {} // do nothing
                        else -> throw IllegalStateException()
                    }
                    toProcess = toProcess.drop(1)
                }
            }
            return Packet(data)
        }
    }
}

data class Value(
    val number: Int,
    val depth: Int
)
